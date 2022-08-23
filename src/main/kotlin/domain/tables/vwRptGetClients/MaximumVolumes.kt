package domain.tables.vwRptGetClients

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object MaximumVolumes : IdTable<Long>("maximum_volumes") {
  override val id: Column<EntityID<Long>> = long("id").autoIncrement("seq_maximum_volumes").entityId()
  override val primaryKey = PrimaryKey(id)

  val base = varchar("base", 32)
  val maxSum = decimal("max_sum", 20, 20)


  val convClient = reference("conv_client", ConvClient)
}
