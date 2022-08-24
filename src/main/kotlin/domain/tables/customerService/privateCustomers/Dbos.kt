package domain.tables.customerService.privateCustomers

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date

object Dbos : IdTable<Long>("dbos") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_dbos").entityId()
  override val primaryKey = PrimaryKey(id)

  val obslIb = varchar("obsl_ib", 64)
  val dataIb = date("data_ib")
  val loginIb = varchar("login_ib", 64)
  val blockIb = varchar("block_ib", 32)

  val privateCustomer = reference("private_customer", PrivateCustomers)
}
