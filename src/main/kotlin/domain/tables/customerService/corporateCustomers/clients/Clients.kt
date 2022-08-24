package domain.tables.customerService.corporateCustomers.clients

import domain.tables.customerService.corporateCustomers.CorporateCustomers
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date

object Clients : IdTable<Long>("clients") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_clients").entityId()
  override val primaryKey = PrimaryKey(id)

  val officialId = long("official_id").nullable().index()
  val dboIdPriv = varchar("dbo_id_priv", 64).nullable()
  val name = varchar("name", 128).nullable()
  val status = varchar("status", 32).nullable()
  val snils = varchar("snils", 64).nullable()
  val gender = varchar("gender", 32).nullable()
  val birthdate = date("birthdate").nullable()
  val vatin = varchar("vatin", 32).nullable()
  val docCode = varchar("doc_code", 32).nullable()
  val docSeries = varchar("doc_series", 32).nullable()
  val docNumber = varchar("doc_number", 32).nullable()
  val docIssuerDate = date("doc_issuer_date").nullable()
  val docIssuer = varchar("doc_issuer", 128).nullable()
  val docIssuerCode = varchar("doc_issuer_code", 32).nullable()
  val signType = varchar("sign_type", 32).nullable()
  val workEnd = date("work_end").nullable()
  val chief = varchar("chief", 32).nullable()
  val holding = varchar("holding", 32).nullable()
  val superUser = varchar("super_user", 32).nullable()
  val useTokenPriv = varchar("use_token_priv", 32).nullable()
  val familyCl = varchar("family_cl", 64).nullable()
  val nameCl = varchar("name_cl", 64).nullable()
  val snameCl = varchar("sname_cl", 64).nullable()

  val dboid = reference("dboid", CorporateCustomers.dboid).index()
}
