package domain.tables.accountService.corporateAccounts

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime

object CorporateAccounts : IdTable<Long>("corporate_accounts") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_corporate_accounts").entityId()
  override val primaryKey = PrimaryKey(id)

  val dboid = long("dboid").index()

  val accSaldo = decimal("acc_saldo", 40, 20)
  val ovdLimit4 = decimal("ovd_limit4", 40, 20)
  val acctId = long("acct_id")
  val acctNum = varchar("acct_num", 32)
  val state = varchar("state", 32).index()
  val depart = varchar("depart", 32).nullable()
  val bic = varchar("bic", 32).nullable()
  val isWork = varchar("is_work", 16)
  val isCard = bool("is_card")
  val acctToProduct = varchar("acct_to_product", 32).nullable()
  val prodType = varchar("prod_type", 32).nullable()
  val acctTarget = varchar("acct_target", 64).nullable()
  val rkoLimPrioritet5 = varchar("rko_lim_prioritet5", 64)
  val rkoLimMinSum6 = varchar("rko_lim_min_sum6", 64)
  val rkoAddAgrMinRest7 = varchar("rko_add_agr_min_rest7", 64)
  val docNotPayDt8 = varchar("doc_not_pay_dt8", 64)
  val docNotPayKt9 = varchar("doc_not_pay_kt9", 64)
  val email = varchar("email", 128).nullable()
  val acctVid = varchar("acct_vid", 64).nullable()
  val prioritet = varchar("prioritet", 64)
  val target = varchar("target", 128).nullable()
  val executoryLimit = varchar("executory_limit", 64)
  val rkoLim44Fz = varchar("rko_lim_44_fz", 64)
  val dateOp = datetime("date_op").nullable()
  val cardTypeCode = varchar("card_type_code", 64).nullable()
}
