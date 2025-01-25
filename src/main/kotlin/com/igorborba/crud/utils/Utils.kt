package com.igorborba.crud.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
val objectMapper = jacksonObjectMapper()

class Utils <T> {
    companion object {
        fun <T> convertValue(value: Any, toValueType: Class<T>): T {
            return objectMapper.convertValue(value, toValueType)
        }
    }
}