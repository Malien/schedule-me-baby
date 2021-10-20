package ua.edu.ukma.dudes.scheduleMeBaby.exception

class InvalidArgumentException : RuntimeException {
    constructor(): super()
    constructor(message: String): super(message)
}