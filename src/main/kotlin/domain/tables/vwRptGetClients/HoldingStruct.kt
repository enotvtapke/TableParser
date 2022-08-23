package domain.tables.vwRptGetClients

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object HoldingStruct : IdTable<Long>("holding_struct") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_holding_struct").entityId()
  override val primaryKey = PrimaryKey(id)

  val orgDboId = varchar("org_dbo_id", 32)
  val orgStatus = varchar("org_status", 64)

  val vwRptGetClients = reference("vw_rpt_get_client", VwRptGetClients)
}
