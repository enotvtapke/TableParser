package domain.tables.customerService.privateCustomers

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object Companies : IdTable<Long>("companies") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_companies").entityId()
  override val primaryKey = PrimaryKey(id)

  val companyName = varchar("company_name", 128)
  val companyRegNum = varchar("company_reg_num", 32)

  val privateCustomer = reference("private_customer", PrivateCustomers)
}
