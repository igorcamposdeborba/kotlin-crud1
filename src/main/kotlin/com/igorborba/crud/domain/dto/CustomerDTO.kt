package com.igorborba.crud.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.igorborba.crud.domain.valueObjects.CustomerStatus
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@JsonIgnoreProperties(ignoreUnknown = true)
data class CustomerDTO(
    @field:Nullable
    var id: String?,
    @field:NotBlank (message = "O nome é obrigatório")
    var name: String,
    @field:NotBlank (message = "O e-mail é obrigatório")
    @field:Email (message = "O e-mail foi digitado errado")
    val email: String,
    var cpf: String?,
    var status: CustomerStatus?
) {
}