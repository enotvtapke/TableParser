package builders.entityBuilder

import builders.Builder
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.JavaLocalDateColumnType
import org.jetbrains.exposed.sql.javatime.JavaLocalDateTimeColumnType
import org.jetbrains.exposed.sql.javatime.JavaLocalTimeColumnType
import java.nio.file.Path

class EntityBuilder : Builder("\t") {
    private enum class Import(val type: String) {
        BigDecimal("java.math.BigDecimal"),
        LocalTime("java.time.LocalDate"),
        LocalDate("java.time.LocalDate"),
        LocalDateTime("java.time.LocalDateTime")
    }

    private val entitiesBasePackage = "domain.entities."
    override val buildedFilesBaseFolder: Path =
        Path.of("/home/lipt/IdeaProjects/TableParser/src/main/kotlin/domain/entities")

    private val imports: MutableList<Import> = mutableListOf()

    fun build(table: Table, className: String) {
        val packageName = entitiesBasePackage +
                getPackage(table.javaClass.canonicalName)
                    .removePrefix(tablesBasePackage)
                    .removeSuffix(tablesBasePackage.javaClass.name)

        val packageCode = "package $packageName\n"

        val classCode = buildString {
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
        imports.clear()

        val code = packageCode + "\n" + (if (importsCode != "") importsCode + "\n" else "") + classCode

        createFile(table, "$className.kt", code)
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

            else -> throw EntityBuildException("Unknown column type ${this.javaClass}")
        }

        return type + if (this.nullable) "?" else ""
    }

    private fun StringBuilder.dataClass(className: String, block: StringBuilder.() -> Unit) =
        element("data class $className(\n", ")") {
            block()
        }

    fun StringBuilder.column(column: Column<*>) = element(
        "val ${column.name.toCamelCase()}: ${column.columnType.asKotlinType()},\n"
    ) {}

    private fun getPackage(canonicalName: String) = canonicalName.dropLastWhile { it != '.' }.dropLast(1)

    private fun String.toCamelCase() = buildString {
        var flag = false
        for (c in this@toCamelCase) {
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

//    private fun String.toSingular() =
//        if (this.endsWith("es")) {
//            this.removeSuffix("es")
//        } else if (this.endsWith("s")) {
//            this.removeSuffix("s")
//        } else {
//            this
//        }
//
//    private fun getClassName(tableName: String): String {
//        val className = tableName.getUnqualifiedName().toCamelCase()
//            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
//        return className.toSingular()
//
//    }
//private fun String.getSchema(): String {
//    val schemaRegex = Regex("^([^.]+)\\.")
//    return schemaRegex.find(this)?.groups?.get(1)?.value ?: ""
//}
//
//    private fun String.getUnqualifiedName(): String {
//        val schemaRegex = Regex("[^.]+$")
//        return schemaRegex.find(this)?.value ?: ""
//    }
}