package com.igorborba.crud.service

import com.igorborba.crud.domain.dto.PurchaseDTO
import com.igorborba.crud.domain.entities.Purchase

class PurchaseMapper(
    private val bookService: BookService,
    private val customerService: CustomerService) {

    fun purchaseDTOtoEntity(request: PurchaseDTO): Purchase {
        val books = bookService.findById(request.bookIds)
        val customer = customerService.findById(request.customerId)

        return Purchase(
            customer = customer,
            books = books.toMutableList(),
            totalPrice = books.sumOf { it.price }
        )
    }
}