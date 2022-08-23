package domain.parser.table

import org.jetbrains.exposed.sql.IColumnType

data class MyColumn(
    var name: String? = null,
    var entityName: String? = null,
    val constraints: MutableList<MyConstraint> = mutableListOf(),
    var type: IColumnType? = null
)