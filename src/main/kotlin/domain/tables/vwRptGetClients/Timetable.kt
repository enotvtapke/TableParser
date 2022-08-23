package domain.tables.vwRptGetClients

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.time

object Timetable : IdTable<Long>("timetable") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_timetable").entityId()
  override val primaryKey = PrimaryKey(id)

  val paymentExttimeFrom = time("payment_exttime_from")
  val paymentExttimeTo = time("payment_exttime_to")
  val paymentIntExttimeFrom = time("payment_int_exttime_from")
  val paymentIntExttimeTo = time("payment_int_exttime_to")
  val currencyExttimeFrom = time("currency_exttime_from")
  val currencyExttimeTo = time("currency_exttime_to")
  val salaryExttimeFrom = time("salary_exttime_from")
  val salaryExttimeTo = time("salary_exttime_to")
  val exchangeExttimeFrom = time("exchange_exttime_from")
  val exchangeExttimeTo = time("exchange_exttime_to")
  val exchangeNotTodayExttimeFrom = time("exchange_not_today_exttime_from")
  val exchangeNotTodayExttimeTo = time("exchange_not_today_exttime_to")
  val exchangeTodayPartialCollateralExttimeFrom = time("exchange_today_partial_collateral_exttime_from")
  val exchangeTodayPartialCollateralExttimeTo = time("exchange_today_partial_collateral_exttime_to")

  val vwRptGetClients = reference("vw_rpt_get_client", VwRptGetClients)
}
