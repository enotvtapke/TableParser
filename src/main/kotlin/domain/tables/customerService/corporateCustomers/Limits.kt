package domain.tables.customerService.corporateCustomers

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object Limits : IdTable<Long>("limits") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_limit").entityId()
  override val primaryKey = PrimaryKey(id)

  val smsTransactionDay = varchar("sms_transaction_day", 32)
  val edsTransactionDay = varchar("eds_transaction_day", 32)
  val smsConversionDay = varchar("sms_conversion_day", 32)
  val edsConversionDay = varchar("eds_conversion_day", 32)
  val minAmountConfirm = varchar("min_amount_confirm", 32).nullable()

  val corporateCustomerDboid = reference("corporate_customer_dboid", CorporateCustomers.dboid).uniqueIndex()
}
