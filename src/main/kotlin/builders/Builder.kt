package builders

abstract class Builder(private val indentSymbol: String) {
    private var indent: Int = 0

    protected val tablesPackagePrefix = "domain.tables."

    private fun StringBuilder.add(content: String) {
        append(indentSymbol.repeat(indent) + content)
    }

    fun StringBuilder.element(prefix: String, suffix: String, indentNumber: Int = 1, block: StringBuilder.() -> Unit) {
        add(prefix)
        indent += indentNumber
        block()
        indent -= indentNumber
        if (suffix != "") {
            add("$suffix\n")
        }
    }

    fun StringBuilder.element(prefix: String, block: StringBuilder.() -> Unit) =
        element(prefix = prefix, suffix ="", block = block)
}