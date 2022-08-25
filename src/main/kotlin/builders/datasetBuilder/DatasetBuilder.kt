package builders.datasetBuilder

import Config
import builders.AbstractBuilder
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.JavaLocalDateColumnType
import org.jetbrains.exposed.sql.javatime.JavaLocalDateTimeColumnType
import org.jetbrains.exposed.sql.javatime.JavaLocalTimeColumnType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DatasetBuilder(private val config: Config) : AbstractBuilder("  ", config) {
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
        if (column.table.primaryKey?.columns?.size == null) return false

        if (column.table.primaryKey?.columns?.size!! > 1) {
            throw DatasetBuildException("Composite primary key columns are not supported.")
        }
        return column.table.primaryKey?.columns?.size!! == 1 && column.table.primaryKey?.columns?.contains(column) == true
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
            else -> throw DatasetBuildException("Unknown column type ${this.javaClass}")
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

    private fun StringBuilder.sequences(table: Table) {
        table.columns.forEach {
            var column = it
            if (it.columnType is EntityIDColumnType<*>) {
                column = (it.columnType as EntityIDColumnType<*>).idColumn
            }
            if (column.columnType is AutoIncColumnType) {
                yamlArrayElement("createSequence") {
                    yamlElement(
                        "sequenceName",
                        (column.columnType as AutoIncColumnType).autoincSeq
                            ?: throw DatasetBuildException("No sequence name specified for autoInc column ${column.name}")
                    )
                    yamlElement("startValue", "1")
                }
            }
        }
    }

    private fun StringBuilder.indices(table: Table) {
        table.indices.forEach { index ->
            yamlArrayElement("createIndex") {
                yamlElement("columns") {
                    index.columns.forEach { column ->
                        yamlArrayElement("column") {
                            yamlElement("name", column.name)
                        }
                    }
                }
                yamlElement("indexName", index.indexName)
                yamlElement("tableName", table.tableName)
                if (index.unique) {
                    yamlElement("unique", "true")
                }
            }
        }
    }

    private fun StringBuilder.foreignKeys(table: Table) {
        table.foreignKeys.forEach {
            yamlArrayElement("addForeignKeyConstraint") {
                yamlElement("baseColumnNames", it.from.joinToString(", ") { it.name })
                yamlElement("baseTableName", it.fromTable.tableName)
                yamlElement("constraintName", it.fkName)
                yamlElement("referencedColumnNames", it.target.joinToString(", ") { it.name })
                yamlElement("referencedTableName", it.targetTable.tableName)
            }
        }
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
                        indices(table)
                        foreignKeys(table)
                    }
                }
            }
        }

        val fileName =
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "-" + table.tableName.replace("_", "-")

        createFile(table, config.datasetBaseFolder, "$fileName.yaml", code)
    }
}