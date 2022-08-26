import builders.Config
import builders.datasetBuilder.DatasetBuilder
import builders.entityBuilder.EntityBuilder
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import java.nio.file.Path

val windowsConfig = Config(
    tablesBasePackage = "domain.tables.",
    entityBasePackage = "domain.entities.",
    entityBaseFolder = Path.of("C:\\Users\\super\\work\\TableParser\\entities"),
    datasetBaseFolder = Path.of("C:\\Users\\super\\work\\TableParser\\resources"),
)

val linuxConfig = Config(
    tablesBasePackage = "domain.tables.",
    entityBasePackage = "domain.entities.",
    entityBaseFolder = Path.of("/home/lipt/IdeaProjects/TableParser/entities"),
    datasetBaseFolder = Path.of("/home/lipt/IdeaProjects/TableParser/resources"),
)

fun main() {
    DbService.setupDb()
    val entityBuilder = EntityBuilder(linuxConfig)
    val datasetBuilder = DatasetBuilder(linuxConfig)

    fun buildEntityAndDataset(table: Table, entityName: String) {
        transaction {
            entityBuilder.build(table, entityName)
            datasetBuilder.build(table)
        }
    }

//    buildEntityAndDataset(CorporateAccounts, "CorporateAccount")
//    buildEntityAndDataset(PrivateAccounts, "PrivateAccount")
//    buildEntityAndDataset(AddAgrs, "AddAgr")
//    buildEntityAndDataset(Deposits, "Deposit")
//    buildEntityAndDataset(NotPayDocs, "NotPayDoc")
//    buildEntityAndDataset(PayLimits, "PayLimit")
}