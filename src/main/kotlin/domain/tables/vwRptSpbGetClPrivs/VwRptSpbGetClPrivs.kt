package domain.tables.vwRptSpbGetClPrivs

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date

object VwRptSpbGetClPrivs : IdTable<Long>("vw_rpt_spb_get_cl_priv") {
  override val id: Column<EntityID<Long>> = long("id").entityId() // customerId aka dboId aka id
  override val primaryKey = PrimaryKey(id)

  // TODO What length?
//  val dbo = text("dbo") //xml
//  val contacts = text("contacts") //xml
//  val address = text("address") //xml
//  val doc = text("doc") //xml
//  val company = text("company") //xml
  val categories = varchar("categories", 1024).nullable()
  val countryTxt = varchar("country_txt", 1024)
  val familyCl = varchar("family_cl", 128)
  val nameCl = varchar("name_cl", 128)
  val snameCl = varchar("sname_cl", 128).nullable()
  val absbranchid = varchar("absbranchid", 128).nullable()
  val clientId = long("client_id")
  val customerId = long("customer_id")
  val iName = varchar("i_name", 128).nullable()
  val isResident = varchar("is_resident", 32)
  val inn = varchar("inn", 64).nullable()
  val kpp = varchar("kpp", 64).nullable()
  val datePers = date("date_pers").nullable()
  val country = varchar("country", 128).nullable()
  val countryAlfa3 = varchar("country_alfa3", 128).nullable()
  val isPensioner = varchar("is_pensioner", 32).nullable()
  val sex = varchar("sex", 32).nullable()
  val complianceControl = varchar("compliance_control", 32).nullable()
}
