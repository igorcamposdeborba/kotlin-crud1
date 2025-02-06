package com.igorborba.crud.service

import com.igorborba.crud.domain.dto.PurchaseDTO
import com.igorborba.crud.domain.entities.Book
import com.igorborba.crud.domain.entities.Customer
import com.igorborba.crud.domain.entities.Purchase
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(
    private val customerService: CustomerService,
    private val bookService: BookService) {

    fun purchaseDTOtoEntity(request: PurchaseDTO): Purchase {
        val customer: Customer = customerService.findById(request.customerId)
        val books: List<Book> = bookService.findById(request.bookIds)

        return Purchase(
            customer = customer,
            books = books.toMutableList(),
            totalPrice = books.sumOf { it.price }
        )
    }
}