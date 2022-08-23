package domain.parser

import domain.parser.TableParser.parseTable
import domain.parser.table.MyColumn
import domain.parser.table.MyConstraint.NULLABLE
import domain.tables.BspbIncomeViews
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import domain.parser.table.MyConstraint.PRIMARY_KEY
import domain.parser.table.MySequence
import domain.parser.table.MyTable

fun main() {
    DbService.setupDb()
    transaction {
        parseTable(BspbIncomeViews)
    }
}

object TableParser {
    private fun getSchema(name: String): String {
        val schemaRegex = Regex("^([^.]+)\\.")
        return schemaRegex.find(name)?.groups?.get(1)?.value ?:""
    }

    private fun getUnqualifiedName(name: String): String {
        val schemaRegex = Regex("[^.]+$")
        return schemaRegex.find(name)?.value ?:""
    }

    fun parseTable(table: Table) {
        val myTable = MyTable(table.tableName)
        table.indices
        table.foreignKeys
        println(myTable)
        table.columns.forEach {
            myTable.columns.add(parseColumn(it))
            if (it.columnType.isAutoInc) {
                myTable.sequences.add(MySequence("seq_${it.name}"))
            }
        }
    }

    fun parseColumn(column: Column<*>): MyColumn {
        column.descriptionDdl()
        val myColumn = MyColumn()
        val tr = TransactionManager.current()
        myColumn.name = tr.identity(column)
        if (column.table.primaryKey?.columns?.contains(column) == true) {
            myColumn.constraints.add(PRIMARY_KEY)
        }
        val type = column.columnType
        myColumn.type = type
        if (type.nullable) {
            myColumn.constraints.add(NULLABLE)
        }
        return myColumn
    }
}