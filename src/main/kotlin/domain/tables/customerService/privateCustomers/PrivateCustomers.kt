package domain.tables.customerService.privateCustomers

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date

object PrivateCustomers : IdTable<Long>("private_customers") {
  override val id: Column<EntityID<Long>> = long("id").entityId() // customerId aka dboId aka id
  override val primaryKey = PrimaryKey(id)

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
