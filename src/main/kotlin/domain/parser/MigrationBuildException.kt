package domain.parser

class MigrationBuildException: RuntimeException {
    constructor(message: String, exception: Exception) : super(message, exception)
    constructor(message: String) : super(message)
}