package com.igorborba.crud.domain.exceptions.exceptionTypes

class NotFoundException ( // sobrescrever atributos da Exception pai
    override val message: String,
    val errorCode: String
    ): Exception() { // herança
}