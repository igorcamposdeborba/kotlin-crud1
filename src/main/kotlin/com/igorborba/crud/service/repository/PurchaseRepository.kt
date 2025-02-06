package com.igorborba.crud.service.repository

import com.igorborba.crud.domain.entities.Purchase
import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository: JpaRepository<Purchase, Int> {
}