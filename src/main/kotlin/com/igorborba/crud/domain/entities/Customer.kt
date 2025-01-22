package com.igorborba.crud.domain.entities

import java.util.UUID

class Customer(
    val id: UUID,
    var name: String,
    var email: String
) {
}