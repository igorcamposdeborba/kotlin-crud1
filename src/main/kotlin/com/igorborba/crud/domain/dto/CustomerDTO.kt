package com.igorborba.crud.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.igorborba.crud.domain.exceptions.exceptionsMessages.ExceptionAttributes
import com.igorborba.crud.domain.exceptions.exceptionsMessages.ExceptionMessages
import com.igorborba.crud.domain.exceptions.validation.EmailAvailable
import com.igorborba.crud.domain.valueObjects.CustomerStatus
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@JsonIgnoreProperties(ignoreUnknown = true)
data class CustomerDTO(
    @field:Nullable
    var id: String?,
    var name: String?,
    @field:NotBlank (message = ExceptionAttributes.EMAIL_REQUIRED)
    @field:Email (message = ExceptionAttributes.EMAIL_MALFORMED)
    @field:EmailAvailable // annotation criada por mim de exception para validação: para economizar consultas complexas ao banco de dados
    val email: String,
    var cpf: String?,
    var status: CustomerStatus?
) {
}