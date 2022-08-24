package domain.tables.customerService.corporateCustomers.clients

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object ProductAccesses : IdTable<Long>("product_accesses") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_product_accesses").entityId()
  override val primaryKey = PrimaryKey(id)

  val deposits = varchar("deposits", 32)
  val loans = varchar("loans", 32)
  val salary = varchar("salary", 32)
  val acquiring = varchar("acquiring", 32)
  val statement = varchar("statement", 32)
  val statementOper = varchar("statement_oper", 32)
  val statementCredit = varchar("statement_credit", 32)
  val statementDebit = varchar("statement_debit", 32)
  val currency = varchar("currency", 32)

  val client = reference("client", Clients).index()
}
