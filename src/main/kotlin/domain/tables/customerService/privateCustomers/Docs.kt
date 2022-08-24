package domain.tables.customerService.privateCustomers

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date

object Docs : IdTable<Long>("docs") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_docs").entityId()
  override val primaryKey = PrimaryKey(id)

  val ser = varchar("ser", 32)
  val num = long("num")
  val type = varchar("type", 32)
  val date = date("date")
  val who = varchar("who", 128)
  val departCode = varchar("depart_code", 32)
  val birthPlace = varchar("birth_place", 128)

  val privateCustomer = reference("private_customer", PrivateCustomers)
}
