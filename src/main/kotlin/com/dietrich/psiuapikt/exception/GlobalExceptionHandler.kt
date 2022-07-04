package com.dietrich.psiuapikt.exception

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.Date

data class ErrorDetails(
    val message: String,
    val details: String,
    val timestamp: Date = Date()
)

class APIException(message: String) : RuntimeException(message)

class NotFoundException(type: String, field: String, value: Any)
    : Exception("$type not found for ${field}: $value")

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(APIException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleAPIException(exception: APIException, wr: WebRequest): ErrorDetails {
        return build(exception, wr)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(exception: NotFoundException, wr: WebRequest): ErrorDetails {
        return build(exception, wr)
    }

    @ExceptionHandler(BadCredentialsException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleBadCredentialsException(exception: BadCredentialsException, wr: WebRequest): ErrorDetails {
        return build(exception, wr)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(exception: Exception, wr: WebRequest): ErrorDetails {
        return build(exception, wr)
    }

    private fun build(exception: Exception, wr: WebRequest): ErrorDetails =
        ErrorDetails(exception.message ?: "", wr.getDescription(false))
}