package builders.datasetBuilder

import builders.Builder
import builders.entityBuilder.EntityBuildException
import builders.entityBuilder.EntityBuilder
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.JavaLocalDateColumnType
import org.jetbrains.exposed.sql.javatime.JavaLocalDateTimeColumnType
import org.jetbrains.exposed.sql.javatime.JavaLocalTimeColumnType
import org.jetbrains.exposed.sql.vendors.SQLiteDialect
import org.jetbrains.exposed.sql.vendors.currentDialect
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DatasetBuilder : Builder("  ") {
    private fun StringBuilder.yamlElement(name: String, block: StringBuilder.() -> Unit) =
        element("$name:\n", block)

    private fun StringBuilder.yamlArrayElement(name: String, block: StringBuilder.() -> Unit) =
        element("- $name:\n", suffix = "", indentNumber = 2, block)

    private fun StringBuilder.content(content: String) {
        deleteCharAt(length - 1)
        append(" $content\n")
    }

    private fun StringBuilder.yamlElement(name: String, content: String) =
        yamlElement(name) { content(content) }

    private fun isPrimaryKey(column: Column<*>): Boolean {
        if (column.table.primaryKey?.columns?.size != null && column.table.primaryKey?.columns?.size!! > 1) {
            throw DatasetBuildException("Composite primary key columns are not supported.")
        }
        return column.table.primaryKey?.columns?.contains(column) == true
    }

    private fun StringBuilder.column(column: Column<*>) {
        yamlArrayElement("column") {
            yamlElement("name", column.name)
            yamlElement("type", column.columnType.asLiquibaseType())
            yamlElement("constraints") {
                if (isPrimaryKey(column)) yamlElement("primaryKey", "true")

                val isAutoIncColumn = column.columnType.isAutoInc
                if (isAutoIncColumn) {
                    yamlElement("autoIncrement", "true")
                }
                val isUnique =
                    column.table.indices.map { it.columns.size == 1 && it.columns.first() == column }.any { it }
                if (isUnique) {
                    yamlElement("unique", "true")
                }
                yamlElement("nullable", column.columnType.nullable.toString())
            }
        }
    }

    private fun StringBuilder.table(table: Table) {
        yamlArrayElement("createTable") {
            yamlElement("tableName", table.tableName)
            yamlElement("columns") {
                table.columns.forEach {
                    column(it)
                }
            }
        }
    }

    private fun StringBuilder.sequences(table: Table) {
        table.columns.forEach {
            if (it.columnType is AutoIncColumnType) {
                yamlElement("createSequence") {
                    yamlElement("sequenceName", (it.columnType as AutoIncColumnType).autoincSeq ?: "")
                    yamlElement("startValue", "1")
                }
            }
        }
    }

    private fun IColumnType.asLiquibaseType(): String =
        when (val column = this) {
            is LongColumnType, is EntityIDColumnType<*> -> "number(20)"
            is DecimalColumnType -> "decimal(${column.precision}, ${column.scale})"
            is VarCharColumnType -> "varchar2(${column.colLength})"
            is TextColumnType -> "clob"
            is IntegerColumnType -> "integer"
            is BooleanColumnType -> "bool"
            is JavaLocalDateTimeColumnType -> "timestamp"
            is JavaLocalDateColumnType -> "date"
            is JavaLocalTimeColumnType -> "time"
            else -> throw DatasetBuildException("Unknown column type ${this.javaClass}");
        }

    fun build(table: Table) {
        val code = buildString {
            yamlElement("databaseChangeLog") {
                yamlArrayElement("changeSet") {
                    yamlElement("id") {
                        content(
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))
                        )
                    }
                    yamlElement("author") { content("LiptSoft") }
                    yamlElement("changes") {
                        table(table)
                        sequences(table)
                    }
                }
            }
        }
        println(code)
        File("/home/lipt/IdeaProjects/TableParser/src/main/kotlin/builders/datasetBuilder/test.yaml").printWriter()
            .use {
                it.print(code)
            }
    }
}