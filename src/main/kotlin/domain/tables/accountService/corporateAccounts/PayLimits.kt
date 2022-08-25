package domain.tables.accountService.corporateAccounts

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date

object PayLimits : IdTable<Long>("pay_limits") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_pay_limits").entityId()
  override val primaryKey = PrimaryKey(id)

  val dboid = long("dboid")
  val acctId = reference("acct_id", CorporateAccounts)

  val limNum = varchar("lim_num", 32)
  val limDate = date("lim_date")
  val sumLimit = decimal("sum_limit", 40, 20)
  val sumLimitRub = decimal("sum_limit_rub", 40, 20)
  val reason = varchar("reason", 32)
  val dateBeg = varchar("date_beg", 32)
  val kindLimit = varchar("kind_limit", 32)

  init {
    index(false, dboid, acctId)
  }
}
