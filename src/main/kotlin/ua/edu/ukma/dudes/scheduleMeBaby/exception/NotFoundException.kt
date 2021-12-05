package ua.edu.ukma.dudes.scheduleMeBaby.exception

open class NotFoundException(override val message: String) : RuntimeException()

class SubjectNotFoundException(id: Long) : NotFoundException("Subject with id $id does not exist")
