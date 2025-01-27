package com.igorborba.crud.domain.exceptions

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExceptionTemplate (
    var timestamp: LocalDateTime,
    var code: Int,
    var errorCode: String,
    var errors: List<FieldErrorResponse>?
){
}