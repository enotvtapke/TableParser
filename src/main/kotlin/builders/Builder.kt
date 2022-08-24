package builders

import org.jetbrains.exposed.sql.Table
import java.io.File
import java.nio.file.Path
import kotlin.io.path.createDirectories

abstract class Builder(private val indentSymbol: String) {
    private var indent: Int = 0

    protected val tablesBasePackage = "domain.tables."
    protected abstract val buildedFilesBaseFolder: Path

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
        element(prefix = prefix, suffix = "", block = block)


    private fun getPackage(canonicalName: String) = canonicalName.dropLastWhile { it != '.' }.dropLast(1)

    fun getTableRelativePath(table: Table): Path = Path.of(
        getPackage(table.javaClass.canonicalName)
            .removePrefix(tablesBasePackage)
            .removeSuffix(tablesBasePackage.javaClass.name)
            .replace(".", File.separator)
    )

    fun createFile(table: Table, fileName: String, content: String) {
        val pathToEntity = buildedFilesBaseFolder.resolve(getTableRelativePath(table))
        pathToEntity.createDirectories()
        File(pathToEntity.resolve(fileName).toAbsolutePath().toString()).printWriter().use {
            it.print(content)
        }
    }
}