package ua.edu.ukma.dudes.scheduleMeBaby.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidArgumentException : RuntimeException()