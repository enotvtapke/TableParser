package domain.tables.customerService

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object BspbIncomeViews : IdTable<Long>("rtdm.bspb_income_view") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("rtdm.seq_bspb_income_view").entityId()
  override val primaryKey = PrimaryKey(id)

  val dboid = long("dboid").index()

  val incomeSum = decimal("income_sum", 20, 20)
  val inn = varchar("inn", 32)
  val orgName = varchar("org_name", 128)

  val fulladdress = varchar("fulladdress", 128).nullable()
  val flatnumber = varchar("flatnumber", 32).nullable()
  val house = varchar("house", 32).nullable()
  val postalcode = varchar("postalcode", 32).nullable()
  val streetcode = varchar("streetcode", 32).nullable()
  val streetname = varchar("streetname", 32).nullable()
  val corps = varchar("corps", 32).nullable()

  val telephone = varchar("telephone", 32)
  val individWageFlg = integer("individ_wage_flg")
  val incConfCode = varchar("inc_conf_code", 32)
}
