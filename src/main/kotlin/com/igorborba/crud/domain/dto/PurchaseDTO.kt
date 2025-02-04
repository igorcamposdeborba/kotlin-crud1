package com.igorborba.crud.domain.dto

import jakarta.validation.constraints.NotNull

data class PurchaseDTO (

    @field:NotNull
    val customerId: String,

    @field:NotNull
    val bookIds: Set<Int>
) {

}