package com.igorborba.crud.domain.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.igorborba.crud.domain.exceptions.exceptionsMessages.ExceptionAttributes
import com.igorborba.crud.domain.valueObjects.BookStatus
import jakarta.annotation.Nullable
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
data class BookDTO(
    @field:Nullable
    var id: Int?,
    @field:NotBlank (message = ExceptionAttributes.TITLE_REQUIRED)
    var title: String,
    @field:NotBlank (message = ExceptionAttributes.PRICE_REQUIRED)
    var price: BigDecimal,
    var status: BookStatus?,

    @field:JsonAlias("customer_id")
    var customerId: String?
) {
}