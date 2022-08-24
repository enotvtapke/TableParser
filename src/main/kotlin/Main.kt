import builders.datasetBuilder.DatasetBuilder
import builders.entityBuilder.EntityBuilder
import domain.tables.accountService.CorporateAccounts
import domain.tables.accountService.Deposits
import domain.tables.accountService.PrivateAccounts
import domain.tables.customerService.privateCustomers.Addresses
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    DbService.setupDb()
    val entityBuilder = EntityBuilder()
    transaction {
        entityBuilder.build(Addresses, "Address")
        entityBuilder.build(CorporateAccounts, "CorporateAccount")
        entityBuilder.build(Deposits, "Deposit")
        entityBuilder.build(PrivateAccounts, "PrivateAccount")
    }

    val datasetBuilder = DatasetBuilder()
    transaction {
        datasetBuilder.build(Addresses)
        datasetBuilder.build(CorporateAccounts)
        datasetBuilder.build(Deposits)
        datasetBuilder.build(PrivateAccounts)
    }
}