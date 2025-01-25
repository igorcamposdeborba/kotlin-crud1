package com.igorborba.crud.domain.dto

import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.util.UUID

class CustomerDTO(
    @Nullable
    var id: UUID?,
    @NotBlank (message = "O nome é obrigatório")
    var name: String,
    @NotBlank (message = "O e-mail é obrigatório")
    @Email (message = "O e-mail foi digitado errado")
    val email: String,
    var cpf: String? = null
) {
//    var id: UUID = UUID.randomUUID()
}