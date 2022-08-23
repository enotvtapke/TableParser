package domain.tables.vwRptSpbGetClPrivs

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date

object Docs : IdTable<Long>("docs") {
  override val id: Column<EntityID<Long>> = long("id").entityId()
  override val primaryKey = PrimaryKey(id)

  val ser = varchar("ser", 32)
  val num = long("num")
  val type = varchar("type", 32)
  val date = date("date")
  val who = varchar("who", 128)
  val departCode = varchar("depart_code", 32)
  val birthPlace = varchar("birth_place", 128)

  val vwRptSpbGetClPriv = reference("vw_rpt_spb_get_cl_priv", VwRptSpbGetClPrivs)
}
