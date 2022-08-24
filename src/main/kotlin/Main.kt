import builders.datasetBuilder.DatasetBuilder
import builders.entityBuilder.EntityBuilder
import domain.tables.BspbIncomeViews
import domain.tables.accountService.Deposits
import domain.tables.vwRptSpbGetClPrivs.VwRptSpbGetClPrivs
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    DbService.setupDb()
//    val entityBuilder = EntityBuilder()
//    transaction {
//        entityBuilder.build(Deposits, "Deposit")
//        entityBuilder.build(BspbIncomeViews, "BspbIncomeView")
//        entityBuilder.build(VwRptSpbGetClPrivs, "VwRptSpbGetClPriv")
//    }

    val datasetBuilder = DatasetBuilder()
    transaction {
        datasetBuilder.build(Deposits)
    }
}