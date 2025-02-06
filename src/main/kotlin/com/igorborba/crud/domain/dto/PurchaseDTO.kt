package com.igorborba.crud.domain.dto

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotNull

data class PurchaseDTO (

    @field:NotNull
    @field:JsonAlias("customer_id")
    val customerId: String,

    @field:NotNull
    @field:JsonAlias(value = ["book_ids"])
    val bookIds: Set<Int>
) {

}