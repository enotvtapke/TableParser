package builders

import org.jetbrains.exposed.sql.Table
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists

abstract class AbstractBuilder(private val indentSymbol: String, config: Config) {
    private var indent: Int = 0

    protected val tablesBasePackage = config.tablesBasePackage

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

    fun createFileIfNotExists(table: Table, baseFolder: Path, fileName: String, content: String) {
        val pathToEntity = baseFolder.resolve(getTableRelativePath(table))
        pathToEntity.createDirectories()
        if (!pathToEntity.resolve(fileName).exists()) {
//            Files.createFile(pathToEntity.resolve(fileName))
            Files.write(pathToEntity.resolve(fileName), content.toByteArray())
        }
//        File(pathToEntity.resolve(fileName).toAbsolutePath().toString()).printWriter().use {
//            it.print(content)
//        }
    }

    fun createOrRewriteFile(table: Table, baseFolder: Path, fileName: String, content: String) {
        val pathToEntity = baseFolder.resolve(getTableRelativePath(table))
        pathToEntity.createDirectories()
        if (!pathToEntity.resolve(fileName).exists()) {
//            Files.createFile(pathToEntity.resolve(fileName))
            Files.write(pathToEntity.resolve(fileName), content.toByteArray())
        }
        File(pathToEntity.resolve(fileName).toAbsolutePath().toString()).printWriter().use {
            it.print(content)
        }
    }
}