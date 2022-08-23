package domain.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object VwRptBspbDboPriv2corps : IdTable<Long>("ibs.vw_rpt_bspb_dbo_priv2corp") {
  override val id: Column<EntityID<Long>> = long("id").entityId() // dboId
  override val primaryKey = PrimaryKey(id)

  val cDboCorpSts = varchar("c_dbo_corp_sts", 1024).nullable()
  val cDboIdCorp = long("c_dbo_id_corp").nullable()
}
