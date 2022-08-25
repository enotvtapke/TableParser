import java.nio.file.Path

data class Config(
    val tablesBasePackage: String,
    val entityBasePackage: String,
    val entityBaseFolder: Path,
    val datasetBaseFolder: Path
)
