package com.igorborba.crud.domain.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

class CustomerDTO(
    @NotBlank (message = "O nome é obrigatório")
    var name: String,
    @NotBlank (message = "O e-mail é obrigatório")
    @Email (message = "O e-mail foi digitado errado")
    var email: String
) {
    var id: UUID
        get() {
            return id
        }
        private set(id: UUID){
            this.id = id
        }
    var cpf: String?
        get() {
            return cpf
        }
        set(cpf: String?){
            this.cpf = cpf
        }
    constructor(name: String, email: String, cpf: String) : this(name, email) {
        this.name = name
        this.email = email
        this.cpf = cpf
    }
    init {
        this.id = UUID.randomUUID()
    }
}