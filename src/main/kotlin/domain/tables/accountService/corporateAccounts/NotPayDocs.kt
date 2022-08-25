package domain.tables.accountService.corporateAccounts

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date

object NotPayDocs : IdTable<Long>("not_pay_docs") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_not_pay_docs").entityId()
  override val primaryKey = PrimaryKey(id)

  val dboid = long("dboid")
  val acctId = reference("acct_id", CorporateAccounts)

  val recordsid = varchar("recordsid", 32).nullable()
  val operDate = varchar("oper_date", 32).nullable()
  val docId = varchar("doc_id", 32)
  val debetDate = varchar("debet_date", 32).nullable()
  val docDate = varchar("doc_date", 32).nullable()
  val prioritet = long("prioritet")
  val documentnumber = varchar("documentnumber", 32)
  val documentdate = date("documentdate")
  val payername = varchar("payername", 64)
  val payerInn = varchar("payer_inn", 32)
  val payerKpp = varchar("payer_kpp", 32)
  val payeraccount = varchar("payeraccount", 32)
  val payerbic = varchar("payerbic", 32)
  val payerbankname = varchar("payerbankname", 64)
  val payercorraccount = varchar("payercorraccount", 32)
  val receivername = varchar("receivername", 64)
  val receiverinn = varchar("receiverinn", 32)
  val receiverkpp = varchar("receiverkpp", 32)
  val receiveraccount = varchar("receiveraccount", 32)
  val receiverbic = varchar("receiverbic", 32)
  val receiverbankname = varchar("receiverbankname", 64)
  val receivercorraccount = varchar("receivercorraccount", 32)
  val isobligations = bool("isobligations")
  val amountopay = decimal("amountopay", 40, 20)
  val trans_amount = varchar("trans_amount", 32).nullable()
  val amountdir = varchar("amountdir", 32)
  val trans_cur = varchar("trans_cur", 16)
  val amount = decimal("amount", 40, 20)
  val currcode = varchar("currcode", 32).nullable()
  val ground = varchar("ground", 512)
  val place = varchar("place", 32).nullable()
  val kodNazn = varchar("kod_nazn", 32).nullable()
  val cardNum = varchar("card_num", 32).nullable()
  val transCountry = varchar("trans_country", 32).nullable()
  val approval = varchar("approval", 32).nullable()

  val budDateExp = varchar("budDateExp", 32).nullable()
  val budDateNu = varchar("bud_date_nu", 32).nullable()
  val budKbkStr = varchar("bud_kbk_str", 32).nullable()
  val budNumExp = varchar("bud_num_exp", 32).nullable()
  val budOkatoStr = varchar("bud_okato_str", 32).nullable()
  val budTaxpayerStr = varchar("bud_taxpayer_str", 32).nullable()
  val budTypeExpStr = varchar("stan", 32).nullable()
  val budKodNaznPayStr = varchar("bud_kod_nazn_pay_str", 32).nullable()
  val userCreate = varchar("user_create", 32)
  val dt_kt = varchar("dt_kt", 32)

  init {
    index(false, dboid, acctId)
  }
}
