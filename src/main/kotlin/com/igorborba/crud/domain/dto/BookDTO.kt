package com.igorborba.crud.domain.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.igorborba.crud.domain.valueObjects.BookStatus
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal
import java.util.UUID

class BookDTO(
    @Nullable
    var id: String?,
    @NotBlank (message = "O nome é obrigatório")
    var name: String,
    @NotBlank (message = "O preço é obrigatório")
    var price: BigDecimal,
    var status: BookStatus?,
    var customer: CustomerDTO?,
    @JsonAlias("customer_id")
    var customerId: String,
) {
}