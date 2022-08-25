package domain.tables.accountService.corporateAccounts

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date

object AddAgrs : IdTable<Long>("add_args") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_add_args").entityId()
  override val primaryKey = PrimaryKey(id)

  val dboid = long("dboid")
  val acctId = reference("acct_id", CorporateAccounts)

  val addAgrNum = varchar("add_agr_num", 32)
  val dateBegining = date("date_begining")
  val dateEnding = varchar("date_ending", 32)
  val minSaldo = decimal("min_saldo", 40, 20)
  val prcRate = decimal("prc_rate", 40, 20)

  init {
    index(false, dboid, acctId)
  }
}
