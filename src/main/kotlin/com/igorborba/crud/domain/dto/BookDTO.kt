package com.igorborba.crud.domain.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.igorborba.crud.domain.valueObjects.BookStatus
import jakarta.annotation.Nullable
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
data class BookDTO(
    @field:Nullable
    var id: Int?,
    @field:NotBlank (message = "O título é obrigatório")
    var title: String,
    @field:NotBlank (message = "O preço é obrigatório")
    var price: BigDecimal,
    var status: BookStatus?,

    @field:JsonAlias("customer_id")
    var customerId: String?
) {
}