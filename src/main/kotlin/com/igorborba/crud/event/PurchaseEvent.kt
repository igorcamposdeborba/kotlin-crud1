package com.igorborba.crud.event

import com.igorborba.crud.domain.entities.Purchase
import org.springframework.context.ApplicationEvent

class PurchaseEvent ( // mapeamento para evento personalizado
    source: Any,
    val purchase: Purchase
): ApplicationEvent(source) {
}