package builders.datasetBuilder

class DatasetBuildException: RuntimeException {
    constructor(message: String, exception: Exception) : super(message, exception)
    constructor(message: String) : super(message)
}