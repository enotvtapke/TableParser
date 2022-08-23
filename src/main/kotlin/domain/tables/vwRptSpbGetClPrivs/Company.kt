package domain.tables.vwRptSpbGetClPrivs

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object Company : IdTable<Long>("company") {
  override val id: Column<EntityID<Long>> = long("id").entityId()
  override val primaryKey = PrimaryKey(id)

  val companyName = varchar("company_name", 128)
  val companyRegNum = varchar("compan_reg_num", 32)

  val vwRptSpbGetClPriv = reference("vw_rpt_spb_get_cl_priv", VwRptSpbGetClPrivs)
}
