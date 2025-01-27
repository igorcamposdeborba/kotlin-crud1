package com.igorborba.crud.domain.exceptions

import com.igorborba.crud.domain.exceptions.exceptionTypes.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime
import java.time.ZoneId

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    fun notFound(exception: NotFoundException, request: WebRequest): ResponseEntity<ExceptionTemplate> {
        return ResponseEntity(ExceptionTemplate(
            timestamp = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
            code = HttpStatus.NOT_FOUND.value(),
            error = exception.errorCode,
            message = exception.message
        ), HttpStatus.NOT_FOUND)
    }
}