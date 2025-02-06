package com.igorborba.crud.event

import com.igorborba.crud.domain.entities.Purchase
import org.springframework.context.ApplicationEvent

class PurchaseEvent (
    source: Any,
    val purchase: Purchase
): ApplicationEvent(source) {
}