package domain.parser.table

data class MyTable(
    var name: String? = null,
    var entityName: String? = null,
    val columns: MutableList<MyColumn> = mutableListOf(),
    val sequences: MutableList<MySequence> = mutableListOf()
)
