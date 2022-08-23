package domain.tables.vwRptGetClients

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date

object QuotedBoard : IdTable<Long>("quoted_board") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_quoted_board").entityId()
  override val primaryKey = PrimaryKey(id)

  val clientGroup = varchar("client_group", 32)
  val minDate = varchar("min_date", 32)
  val maxDate = varchar("max_date", 32)
  val contractLimit = varchar("contract_limit", 32)
  val currentLimit = varchar("current_limit", 32)
  val dateDog = date("date_dog")
  val numDog = varchar("num_dog", 32)
  val account = varchar("account", 32)
//  val derivatives = text("derivatives") // xml

  val vwRptGetClients = reference("vw_rpt_get_client", VwRptGetClients)
}
