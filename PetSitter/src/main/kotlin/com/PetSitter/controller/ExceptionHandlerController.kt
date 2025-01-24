package com.PetSitter.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerController {
    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(exception: NotFoundException) : ResponseEntity<ErrorMessage> {
        return getBody(exception, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ForbiddenException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleForbiddenException(exception: ForbiddenException) : ResponseEntity<ErrorMessage> {
        return getBody(exception, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(exception: BadRequestException) : ResponseEntity<ErrorMessage> {
        return getBody(exception, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleRuntimeException(exception: RuntimeException) : ResponseEntity<ErrorMessage> {
        return getBody(exception, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun getBody(exception: Exception, status: HttpStatus) : ResponseEntity<ErrorMessage> {
        return ResponseEntity(ErrorMessage(status.value(), exception.message), status)
    }
}

data class ErrorMessage (
    var status: Int? = null,
    var message: String? = null
)

class NotFoundException(message: String) : RuntimeException(message)

class BadRequestException(message: String) : RuntimeException(message)

class ForbiddenException(message: String) : RuntimeException(message)
