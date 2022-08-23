package domain.tables.vwRptSpbGetClPrivs

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object Contacts : IdTable<Long>("contacts") {
  override val id: Column<EntityID<Long>> = long("id").entityId()
  override val primaryKey = PrimaryKey(id)

  val contactType = varchar("contact_type", 64)
  val number = varchar("number", 64)
  val active = varchar("active", 16)

  val vwRptSpbGetClPriv = reference("vw_rpt_spb_get_cl_priv", VwRptSpbGetClPrivs)
}
