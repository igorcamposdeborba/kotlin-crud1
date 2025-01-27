package com.igorborba.crud.domain.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.igorborba.crud.domain.valueObjects.BookStatus
import jakarta.annotation.Nullable
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
data class BookDTO(
    @Nullable
    var id: Int?,
    @NotBlank (message = "O título é obrigatório")
    var title: String,
    @NotBlank (message = "O preço é obrigatório")
    var price: BigDecimal,
    var status: BookStatus?,

    @JsonAlias("customer_id")
    var customerId: String?
) {
}