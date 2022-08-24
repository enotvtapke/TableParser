package domain.tables.customerService

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object VwRptAccessDenieds : IdTable<Long>("vw_rpt_access_denied") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_vw_rpt_access_denied").entityId()
  override val primaryKey = PrimaryKey(id)

  val dboid = long("dboid").index()
  val officialId = long("official_id").index()
  val productName = varchar("product_name", 128)
  val productId = varchar("product_id", 128)
}
