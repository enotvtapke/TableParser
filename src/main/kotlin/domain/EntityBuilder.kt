package domain

import domain.parser.DbService
import domain.parser.EntityBuildException
import domain.tables.vwRptGetClients.VwRptGetClients
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.JavaLocalDateColumnType
import org.jetbrains.exposed.sql.javatime.JavaLocalDateTimeColumnType
import org.jetbrains.exposed.sql.javatime.JavaLocalTimeColumnType
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.nio.file.StandardOpenOption.CREATE
import java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
import java.util.*
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile

fun main() {
    DbService.setupDb()
    transaction {
        println(EntityBuilder.build(VwRptGetClients))
    }
}

object EntityBuilder {
    private var indent: Int = 0
    private val imports: MutableList<Import> = mutableListOf()

    private enum class Import(val type: String) {
        BigDecimal("java.math.BigDecimal"),
        LocalTime("java.time.LocalDate"),
        LocalDate("java.time.LocalDate"),
        LocalDateTime("java.time.LocalDateTime")
    }

    private fun String.getSchema(): String {
        val schemaRegex = Regex("^([^.]+)\\.")
        return schemaRegex.find(this)?.groups?.get(1)?.value ?: ""
    }

    private fun String.getUnqualifiedName(): String {
        val schemaRegex = Regex("[^.]+$")
        return schemaRegex.find(this)?.value ?: ""
    }

    private fun String.asEntityName() = buildString {
        var flag = false
        for (c in this@asEntityName) {
            if (c == '_') {
                flag = true
            } else {
                if (flag) {
                    append(c.uppercase())
                    flag = false
                } else {
                    append(c)
                }
            }
        }
    }

    private fun String.toSingular() =
        if (this.endsWith("es")) {
            this.removeSuffix("es")
        } else if (this.endsWith("s")) {
            this.removeSuffix("s")
        } else {
            this
        }

    private fun getClassName(tableName: String): String {
        val className = tableName.getUnqualifiedName().asEntityName()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        return className.toSingular()

    }

    private fun StringBuilder.add(content: String) {
        append("\t".repeat(indent) + content)
    }

    private fun StringBuilder.element(prefix: String, suffix: String, block: StringBuilder.() -> Unit) {
        add(prefix)
        indent++
        block()
        indent--
        if (suffix != "") {
            add(suffix)
        }
    }

    private fun StringBuilder.element(content: String, block: StringBuilder.() -> Unit) =
        element(prefix = content, suffix = "") {}

    private fun StringBuilder.column(column: Column<*>) = element(
        "val ${column.name.asEntityName()}: ${column.columnType.asKotlinType()},\n"
    ) {}

    private fun StringBuilder.dataClass(className: String, block: StringBuilder.() -> Unit) =
        element("data class $className(\n", ")") {
            block()
        }

    private fun IColumnType.asKotlinType(): String {
        val type = when (this) {
            is LongColumnType, is EntityIDColumnType<*> -> "Long"
            is DecimalColumnType -> {
                imports.add(Import.BigDecimal)
                "BigDecimal"
            }
            is VarCharColumnType, is TextColumnType -> "String"
            is IntegerColumnType -> "Int"
            is BooleanColumnType -> "Boolean"
            is JavaLocalDateTimeColumnType -> {
                imports.add(Import.LocalDateTime)
                "LocalDateTime"
            }
            is JavaLocalDateColumnType -> {
                imports.add(Import.LocalDate)
                "LocalDate"
            }
            is JavaLocalTimeColumnType -> {
                imports.add(Import.LocalTime)
                "LocalTime"
            }
            else -> throw EntityBuildException("Unknown column type ${this.javaClass}");
        }

        return type + if (this.nullable) "?" else ""
    }

    private const val tablesPackagePrefix = "domain.tables."
    private const val entitiesBasePackage = "domain.entities."

    private val entitiesBaseFolder = Path.of("C:\\Users\\super\\work\\TableParser\\src\\main\\kotlin\\domain\\entities")

    private fun getPackage(canonicalName: String) = canonicalName.dropLastWhile { it != '.' }.dropLast(1)

    private fun getEntityPackage(table: Table) =
        entitiesBasePackage + getPackage(table.javaClass.canonicalName).removePrefix(tablesPackagePrefix).toSingular()

    private fun entityPackageToPath(entityPackage: String): Path =
        Path.of(entityPackage.removePrefix(entitiesBasePackage).replace(".", File.separator))

    fun build(table: Table): String {
        // TODO add package
        val packageCode = "package ${getEntityPackage(table)}\n"
        val className = getClassName(table.tableName)
        val entityCode = buildString {
            dataClass(className) {
                table.columns.forEach {
                    column(it)
                }
            }
        }

        val importsCode = buildString {
            imports.distinct().forEach {
                append("import ${it.type}\n")
            }
        }

        val code = packageCode + "\n" + importsCode + "\n" + entityCode

        val pathToEntity = entitiesBaseFolder.resolve(entityPackageToPath(getEntityPackage((table))))
        pathToEntity.parent.createDirectories()
        // todo What???
        Files.write(pathToEntity.resolve("$className.kt"), code.toByteArray())
        return code
    }
}