package com.igorborba.crud.domain.exceptions.exceptionsMessages

enum class ExceptionMessages(val code: String,
                             val message: String) {
    EX001("EX-001", "Requisição inválida"),
    EX100("EX-100", "Livro não existe com o id %s"),
    EX200("EX-200", "Usuário não existe com o e-mail %s")
}