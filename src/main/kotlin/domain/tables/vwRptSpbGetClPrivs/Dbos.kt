package domain.tables.vwRptSpbGetClPrivs

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date

object Dbos : IdTable<Long>("dbos") {
  override val id: Column<EntityID<Long>> = long("id").entityId()
  override val primaryKey = PrimaryKey(id)

  val obslIb = long("obsl_ib")
  val dataIb = date("data_ib")
  val loginIb = long("login_ib")
  val blockIb = long("block_ib")

  val vwRptSpbGetClPriv = reference("vw_rpt_spb_get_cl_priv", VwRptSpbGetClPrivs)
}
