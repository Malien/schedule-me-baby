package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ua.edu.ukma.dudes.scheduleMeBaby.exception.InvalidArgumentException
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import java.time.LocalDateTime


@ControllerAdvice
class ExceptionHandlerController : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(NotFoundException::class)])
    fun handleNotFound(ex: NotFoundException, request: WebRequest): ResponseEntity<Any> {
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex.message
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [(InvalidArgumentException::class)])
    fun handleInvalidArgument(ex: InvalidArgumentException, request: WebRequest): ResponseEntity<Any> {
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        if (ex.message != null) {
            body["message"] = ex.message
        }
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

//    TODO how to override it for Kotlin?
//    https://stackoverflow.com/questions/38282298/ambiguous-exceptionhandler-method-mapped-for-class-org-springframework-web-bin
//    @ExceptionHandler(value = [(MethodArgumentNotValidException::class)])
//    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<Any> {
//        val body: MutableMap<String, Any> = LinkedHashMap()
//        body["timestamp"] = LocalDateTime.now()
//        body["message"] = ex.message
//        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
//    }
}