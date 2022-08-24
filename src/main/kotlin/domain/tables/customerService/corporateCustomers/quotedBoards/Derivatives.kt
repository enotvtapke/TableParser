package domain.tables.customerService.corporateCustomers.quotedBoards

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object Derivatives : IdTable<Long>("derivatives") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_derivatives").entityId()
  override val primaryKey = PrimaryKey(id)

  val baseCurr = varchar("base_curr", 32)
  val quoteCurr = varchar("quote_curr", 32)
  val cftTypeFix = varchar("cft_type_fix", 64)
  val i2bTypeFix = varchar("i2b_type_fix", 64)

  val quotedBoard = reference("quoted_board", QuotedBoards)
}
