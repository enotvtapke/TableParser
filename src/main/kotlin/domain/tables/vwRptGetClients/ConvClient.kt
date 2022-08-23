package domain.tables.vwRptGetClients

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date

object ConvClient : IdTable<Long>("conv_client") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_conv_client").entityId()
  override val primaryKey = PrimaryKey(id)

  val clientGroup = varchar("client_group", 32)
  val courseIsWork = varchar("course_is_work", 16)
  val clRate = decimal("cl_rate", 20, 20)
  val reasonName = varchar("reason_name", 512)
  val comment = varchar("comment", 512)
  val convMaxSum = varchar("conv_max_sum", 32)
  val convMaxUrgency = varchar("conv_max_urgency", 32)
  val contractLimit = varchar("contract_limit", 32)
  val currentLimit = varchar("current_limit", 32)
  val dateDog = date("date_dog")
  val numDog = varchar("num_dog", 32)
  val account = varchar("account", 32)

  val vwRptGetClients = reference("vw_rpt_get_client", VwRptGetClients)
}
