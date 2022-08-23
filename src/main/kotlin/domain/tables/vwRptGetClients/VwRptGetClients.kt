package domain.tables.vwRptGetClients

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime

object VwRptGetClients : IdTable<Long>("vw_rpt_get_client") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_vw_rpt_spb_get_cl_priv").entityId()
  override val primaryKey = PrimaryKey(id)

  // TODO What length?
  val dboid = long("dboid").uniqueIndex()
  val corpAddress = text("corp_address") // xml nullable?
  val clientDogs = text("client_dogs").nullable() // xml
  val timetable = text("timetable").nullable() // xml
  val name = varchar("name", 64).nullable()
  val longName = varchar("long_name", 128).nullable()
  val legalForm = integer("legal_form").nullable()
  val bssId = varchar("bss_id", 128).nullable()
  val customerId = long("customer_id")
  val clientId = long("client_id")
  val siName = varchar("si_name", 128).nullable()
  val iName = varchar("i_name", 128).nullable()
  val isResident = integer("is_resident")
  val inn = varchar("inn", 64).nullable()
  val kpp = varchar("kpp", 64).nullable()
  val ogrn = varchar("ogrn", 64).nullable()
  val dateOgrn = datetime("date_ogrn").nullable()
  val dateNotif = date("date_notif").nullable()
  val dateEy = date("date_ey").nullable()
  val segment = integer("segment").nullable()
  val workDay = integer("work_day").nullable()
  val quotedBoard = text("quoted_board").nullable() // xml
  val convClient = text("conv_client").nullable() // xml
  val exchangeAllSigns = integer("exchange_all_signs").nullable()
  val holdingStruct = text("holding_struct").nullable() // xml
  val limit = text("limit").nullable() // xml
  val formaCode = varchar("forma_code", 256).nullable()
  val countryCode = varchar("country_code", 128).nullable()
  val countrySubdivisionCode = varchar("country_subdivision_code", 128).nullable()
  val isAutoPay = integer("is_auto_pay")
}
