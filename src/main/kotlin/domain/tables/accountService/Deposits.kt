package domain.tables.accountService

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object Deposits : IdTable<Long>("deposits") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_deposits").entityId()
  override val primaryKey = PrimaryKey(id)

  val dboid = long("dboid").index()

  val account = varchar("account", 32)
  val active = varchar("active", 32).nullable()
  val depDt = varchar("dep_dt", 32).nullable()
  val depYesAdd = varchar("dep_yes_add", 32).nullable()
}
