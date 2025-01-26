package com.igorborba.crud.domain.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.igorborba.crud.domain.entities.Book
import com.igorborba.crud.domain.entities.Customer
import com.igorborba.crud.domain.valueObjects.BookStatus
import com.igorborba.crud.service.repository.CustomerRepository
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal
import java.util.UUID

@JsonIgnoreProperties(ignoreUnknown = true)
data class BookDTO(
    @Nullable
    var id: Int?,
    @NotBlank (message = "O nome é obrigatório")
    var name: String,
    @NotBlank (message = "O preço é obrigatório")
    var price: BigDecimal,
    var status: BookStatus?,
    @JsonAlias("customer_id")
    var customerId: String?,

) {
}