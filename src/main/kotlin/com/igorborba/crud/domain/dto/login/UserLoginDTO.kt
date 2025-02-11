package com.igorborba.crud.domain.dto.login

import com.igorborba.crud.domain.exceptions.exceptionsMessages.ExceptionAttributes
import com.igorborba.crud.domain.exceptions.validation.EmailAvailable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class UserLoginDTO (
    @field:Email (message = ExceptionAttributes.EMAIL_MALFORMED)
    @field:EmailAvailable // annotation criada por mim de exception para validação: para economizar consultas complexas ao banco de dados
    val email: String,

    @field:NotEmpty(message =  ExceptionAttributes.PASSWORD_REQUIRED)
    var password: String
) {
}