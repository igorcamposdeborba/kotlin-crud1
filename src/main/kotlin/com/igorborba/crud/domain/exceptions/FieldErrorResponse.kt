package com.igorborba.crud.domain.exceptions

data class FieldErrorResponse (
    var message: String,
    var field: String
) {
}