package com.igorborba.crud.service

import com.igorborba.crud.domain.dto.PurchaseDTO
import com.igorborba.crud.domain.entities.Purchase
import com.igorborba.crud.event.PurchaseEvent
import com.igorborba.crud.service.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService (val purchaseMapper : PurchaseMapper,
                       val purchaseRepository: PurchaseRepository,
                       val applicationEventPublisher: ApplicationEventPublisher) {

    fun create(purchaseDTO: PurchaseDTO){
        val purchase : Purchase = purchaseMapper.purchaseDTOtoEntity(purchaseDTO) // converter DTO para Entity do banco de dados
        purchaseRepository.save(purchase) // salvar no banco de dados a compra

        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchase)) // !todo: dispara evento de compra
    }

    fun update(purchase: Purchase): Unit {
        purchaseRepository.save(purchase)
    }
}