package domain.tables.customerService.privateCustomers

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object Contacts : IdTable<Long>("contacts") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_contacts").entityId()
  override val primaryKey = PrimaryKey(id)

  val contactType = varchar("contact_type", 64)
  val number = varchar("number", 64)
  val active = varchar("active", 16).nullable()

  val privateCustomer = reference("private_customer", PrivateCustomers)
}
