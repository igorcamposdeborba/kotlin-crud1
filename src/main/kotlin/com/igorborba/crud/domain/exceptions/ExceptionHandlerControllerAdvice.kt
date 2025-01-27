package com.igorborba.crud.domain.exceptions

import com.igorborba.crud.configs.ConstantConfig
import com.igorborba.crud.domain.exceptions.exceptionTypes.NotFoundException
import com.igorborba.crud.domain.exceptions.exceptionsMessages.ExceptionMessages
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime
import java.time.ZoneId

@ControllerAdvice
class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    fun notFound(exception: NotFoundException, request: WebRequest): ResponseEntity<ExceptionTemplate> {
        return ResponseEntity(ExceptionTemplate(
            timestamp = LocalDateTime.now(ZoneId.of(ConstantConfig.COUNTRY)),
            code = HttpStatus.NOT_FOUND.value(),
            errorCode = exception.errorCode,
            errors = null
        ), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun badRequest(exception: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ExceptionTemplate> {
        return ResponseEntity(ExceptionTemplate(
            timestamp = LocalDateTime.now(ZoneId.of(ConstantConfig.COUNTRY)),
            code = HttpStatus.BAD_REQUEST.value(),
            errorCode = ExceptionMessages.EX100.code,
            errors = exception.bindingResult.fieldErrors.map { FieldErrorResponse(it.defaultMessage ?: "Invalid", it.field) }
        ), HttpStatus.BAD_REQUEST)
    }
}