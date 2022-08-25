import builders.datasetBuilder.DatasetBuilder
import builders.entityBuilder.EntityBuilder
import domain.tables.accountService.CorporateAccounts
import domain.tables.accountService.Deposits
import domain.tables.accountService.PrivateAccounts
import domain.tables.customerService.privateCustomers.Addresses
import org.jetbrains.exposed.sql.transactions.transaction
import java.nio.file.Path

internal fun main() {
    val config = Config(
        tablesBasePackage = "domain.tables.",
        entityBasePackage = "domain.entities.",
        entityBaseFolder = Path.of("C:\\Users\\super\\work\\TableParser\\src\\main\\kotlin\\domain\\entities"),
        datasetBaseFolder = Path.of("C:\\Users\\super\\work\\TableParser\\resources\\liquibase")
    )
    val entityBuilder = EntityBuilder(config)
    val datasetBuilder = DatasetBuilder(config)
    DbService.setupDb()
    transaction {
        entityBuilder.build(Addresses, "Address")
        entityBuilder.build(CorporateAccounts, "CorporateAccount")
        entityBuilder.build(Deposits, "Deposit")
        entityBuilder.build(PrivateAccounts, "PrivateAccount")
    }
    transaction {
        datasetBuilder.build(Addresses)
        datasetBuilder.build(CorporateAccounts)
        datasetBuilder.build(Deposits)
        datasetBuilder.build(PrivateAccounts)
    }
}