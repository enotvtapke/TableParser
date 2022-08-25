package domain.tables.accountService.privateAccounts

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime

object PrivateAccounts : IdTable<Long>("private_accounts") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_private_accounts").entityId()
  override val primaryKey = PrimaryKey(id)

  val dboid = long("dboid").index()

  val acctNum = varchar("acct_num", 32).nullable()
  val acctId = long("acct_id")
  val saldo = decimal("saldo", 40, 20)
  val state = varchar("state", 32)
  val depart = varchar("depart", 32)
  val overAcctId = long("over_acct_id").nullable()
  val prodType = varchar("prod_type", 32).nullable()
  val bic = varchar("bic", 32).nullable()
  val accForCred = varchar("acc_for_cred", 32).nullable()
  val cardProducts = varchar("card_products", 64).nullable()
  val overAcctLim = varchar("over_acct_lim", 32)
  val ismoneybox = bool("ismoneybox")
  val dateOp = datetime("date_op").nullable()
}
