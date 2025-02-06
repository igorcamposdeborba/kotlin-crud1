package com.igorborba.crud.event

import com.igorborba.crud.domain.entities.Purchase
import com.igorborba.crud.service.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

// todo: listener do evento para esta classe criar uma NFE (nota fiscal eletronica)
@Component
class NfeListener (private val purchaseService: PurchaseService) {

    @EventListener
    fun listen(purchaseEvent: PurchaseEvent): Unit {
        val nfe: String = UUID.randomUUID().toString()
        val purchase: Purchase = purchaseEvent.purchase.copy(nfe = nfe) // criar novo objeto purchase que pertence a este evento

        purchaseService.update(purchase)
    }
}