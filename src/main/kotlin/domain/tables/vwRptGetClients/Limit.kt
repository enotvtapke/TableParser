package domain.tables.vwRptGetClients

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object Limit : IdTable<Long>("limit") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_limit").entityId()
  override val primaryKey = PrimaryKey(id)

  val smsTransactionDay = varchar("sms_transaction_day", 32)
  val emailTransactionDay = varchar("email_transaction_day", 32)
  val smsConversionDay = varchar("sms_conversion_day", 32)
  val emailConversionDay = varchar("email_conversion_day", 32)

  val vwRptGetClients = reference("vw_rpt_get_client", VwRptGetClients)
}
