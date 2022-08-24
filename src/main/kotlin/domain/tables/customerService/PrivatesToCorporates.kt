package domain.tables.customerService

import domain.tables.customerService.corporateCustomers.CorporateCustomers
import domain.tables.customerService.privateCustomers.PrivateCustomers
import org.jetbrains.exposed.sql.Table

object PrivatesToCorporates : Table("ibs.privates_to_corporates") {
  val id = reference("id", PrivateCustomers).index("idx_privates_to_corporates_id")
  val cDboIdCorp = reference("c_dbo_id_corp", CorporateCustomers.dboid)

  val cDboCorpSts = varchar("c_dbo_corp_sts", 1024).nullable()

  override val primaryKey = PrimaryKey(id, cDboCorpSts)
}
