package domain.tables.vwRptGetClients

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object ClientDogs : IdTable<Long>("client_dogs") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_client_dogs").entityId()
  override val primaryKey = PrimaryKey(id)

  val absbranchid = varchar("absbranchid", 64)
  val status = varchar("status", 32)

  val vwRptGetClients = reference("vw_rpt_get_client", VwRptGetClients)
}
