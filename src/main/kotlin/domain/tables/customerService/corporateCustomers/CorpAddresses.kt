package domain.tables.customerService.corporateCustomers

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object CorpAddresses : IdTable<Long>("corp_addresses") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_corp_address").entityId()
  override val primaryKey = PrimaryKey(id)

  val latinImpStr = varchar("latin_imp_str", 512)
  val impStr = varchar("imp_str", 1024)
  val cityName = varchar("city_name", 128)
  val region = varchar("region", 128)
  val postCode = varchar("post_code", 32)
  val streetStr = varchar("street_str", 128)
  val house = varchar("house", 32)
  val korpus = varchar("korpus", 32)
  val flat = varchar("flat", 32)

  val corporateCustomer = reference("corporate_customer", CorporateCustomers)
}
