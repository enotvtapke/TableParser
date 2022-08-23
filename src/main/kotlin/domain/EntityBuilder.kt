package domain

import domain.EntityBuilder.asEntityName
import domain.EntityBuilder.getUnqualifiedName
import domain.parser.DbService
import domain.parser.EntityBuildException
import domain.parser.MigrationBuildException
import domain.tables.BspbIncomeViews
import domain.tables.vwRptGetClients.VwRptGetClients
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.JavaLocalDateColumnType
import org.jetbrains.exposed.sql.javatime.JavaLocalDateTimeColumnType
import org.jetbrains.exposed.sql.javatime.JavaLocalTimeColumnType
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

fun main() {
    DbService.setupDb()
    transaction {
        println(EntityBuilder.build(VwRptGetClients))
    }
}

object EntityBuilder {
    private val imports: MutableList<Import> = mutableListOf()

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

    private fun getClassName(tableName: String): String {
        val className = tableName.getUnqualifiedName().asEntityName()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        if (className.endsWith("es")) {
            return className.removeSuffix("es")
        } else if (className.endsWith("s")) {
            return className.removeSuffix("s")
        } else {
            return className
        }

    }

    private var indent: Int = 0

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

    private fun IColumnType.asKotlinType() =
        when (this) {
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

    fun build(table: Table): String {
        // TODO add package
        val packageCode = ""
        val entityCode = buildString {
            dataClass(getClassName(table.tableName)) {
                table.columns.forEach {
                    column(it)
                }
            }
        }
        val importsCode = buildString {
            imports.forEach {
                append("import ${it.type}")
            }
        }
        return packageCode + importsCode + entityCode
    }


    private enum class Import(val type: String) {
        BigDecimal("java.math.$this"),
        LocalDate("java.time.$this"),
        LocalTime("java.time.$this"),
        LocalDateTime("java.time.$this")
    }
}