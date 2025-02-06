package com.igorborba.crud.event

import com.igorborba.crud.service.BookService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

// todo: listener do evento para esta classe alterar o status para vendido o livro
@Component
class UpdateSoldBookListener (private val bookService: BookService) {

    @EventListener
    fun listen(purchaseEvent: PurchaseEvent): Unit {

        bookService.purchase(purchaseEvent.purchase.books)
    }
}