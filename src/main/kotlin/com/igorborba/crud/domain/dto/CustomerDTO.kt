package com.igorborba.crud.domain.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

class CustomerDTO {
    val id: UUID?
    @NotBlank (message = "O nome é obrigatório")
    var name: String?
    @NotBlank (message = "O e-mail é obrigatório")
    @Email (message = "O e-mail foi digitado errado")
    var email: String?

    constructor(id: UUID?,
                name: String?,
                email: String?) {
        this.id = id
        this.name = name
        this.email = email
    }
}