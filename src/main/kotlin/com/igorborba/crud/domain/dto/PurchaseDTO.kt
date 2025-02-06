package com.igorborba.crud.domain.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class PurchaseDTO (

    @field:NotNull
    @field:JsonProperty(value = "customer_id")
    val customerId: String,

    @field:NotNull
    @field:JsonProperty(value = "book_ids")
    val bookIds: Set<Int>
) {

}