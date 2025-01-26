package com.igorborba.crud.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@JsonIgnoreProperties(ignoreUnknown = true)
data class CustomerDTO(
    @Nullable
    var id: String?,
    @NotBlank (message = "O nome é obrigatório")
    var name: String,
    @NotBlank (message = "O e-mail é obrigatório")
    @Email (message = "O e-mail foi digitado errado")
    val email: String,
    var cpf: String? = null
) {
}