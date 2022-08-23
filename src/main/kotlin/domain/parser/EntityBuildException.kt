package domain.parser

class EntityBuildException: RuntimeException {
    constructor(message: String, exception: Exception) : super(message, exception)
    constructor(message: String) : super(message)
}