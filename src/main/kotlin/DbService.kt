import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

internal object DbService {
  fun setupDb() {
    dataSource().also {
      Database.connect(it)
    }
  }

  private fun dataSource(): HikariDataSource = HikariDataSource(
    HikariConfig().apply {
      driverClassName = "org.postgresql.Driver"
      jdbcUrl = "jdbc:postgresql://0.0.0.0:5432/ibs"
      isAutoCommit = true
      username = "admin"
      password = "admin"
      validate()
    }
  )
}
