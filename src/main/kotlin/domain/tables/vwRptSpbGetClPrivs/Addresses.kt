package domain.tables.vwRptSpbGetClPrivs

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object Addresses : IdTable<Long>("addresses") {
  override val id: Column<EntityID<Long>> = long("id").entityId()
  override val primaryKey = PrimaryKey(id)

  val address = varchar("address", 256)
  val cityName = varchar("city_name", 128)
  val placeType = varchar("place_type", 32)
  val region = varchar("region", 128)
  val postCode = varchar("post_code", 32)

  val vwRptSpbGetClPriv = reference("vw_rpt_spb_get_cl_priv", VwRptSpbGetClPrivs)
}
