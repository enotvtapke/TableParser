import builders.Config
import builders.datasetBuilder.DatasetBuilder
import builders.entityBuilder.EntityBuilder
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import domain.tables.accountService.corporateAccounts.AddAgrs
import domain.tables.accountService.corporateAccounts.CorporateAccounts
import domain.tables.accountService.corporateAccounts.NotPayDocs
import domain.tables.accountService.corporateAccounts.PayLimits
import domain.tables.accountService.privateAccounts.Deposits
import domain.tables.accountService.privateAccounts.PrivateAccounts
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import java.nio.file.Path

fun main() {
    // TODO problem with unique. Every index is unique
    DbService.setupDb()
    val config = Config(
        tablesBasePackage = "domain.tables.",
        entityBasePackage = "domain.entities.",
        entityBaseFolder = Path.of("/home/lipt/IdeaProjects/TableParser/entities"),
        datasetBaseFolder = Path.of("/home/lipt/IdeaProjects/TableParser/resources"),
    )
    val entityBuilder = EntityBuilder(config)
    val datasetBuilder = DatasetBuilder(config)

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