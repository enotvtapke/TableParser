import builders.Config
import builders.datasetBuilder.DatasetBuilder
import builders.entityBuilder.EntityBuilder
import domain.tables.accountService.corporateAccounts.AddAgrs
import domain.tables.accountService.corporateAccounts.CorporateAccounts
import domain.tables.accountService.corporateAccounts.NotPayDocs
import domain.tables.accountService.corporateAccounts.PayLimits
import domain.tables.accountService.privateAccounts.Deposits
import domain.tables.accountService.privateAccounts.PrivateAccounts
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
    val entityBuilder = EntityBuilder(windowsConfig)
    val datasetBuilder = DatasetBuilder(windowsConfig)

    fun buildEntityAndDataset(table: Table, entityName: String) {
        transaction {
            entityBuilder.build(table, entityName)
            datasetBuilder.build(table)
        }
    }

    buildEntityAndDataset(CorporateAccounts, "CorporateAccount")
    buildEntityAndDataset(PrivateAccounts, "PrivateAccount")
    buildEntityAndDataset(AddAgrs, "AddAgr")
    buildEntityAndDataset(Deposits, "Deposit")
    buildEntityAndDataset(NotPayDocs, "NotPayDoc")
    buildEntityAndDataset(PayLimits, "PayLimit")
}