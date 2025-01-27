package com.igorborba.crud.domain.exceptions.exceptionsMessages

object ExceptionAttributes {
    const val NAME_REQUIRED: String = "O nome é obrigatório"
    const val EMAIL_REQUIRED: String = "O e-mail é obrigatório"
    const val EMAIL_MALFORMED: String = "O e-mail foi digitado errado"
    const val EMAIL_REGISTERED: String = "O e-mail já está cadastrado"
    const val TITLE_REQUIRED: String = "O título é obrigatório"
    const val PRICE_REQUIRED: String = "O preço é obrigatório"
}