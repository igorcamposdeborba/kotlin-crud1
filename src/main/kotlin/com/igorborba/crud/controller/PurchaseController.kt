package com.igorborba.crud.controller

import com.igorborba.crud.domain.dto.PurchaseDTO
import com.igorborba.crud.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/purchase")
class PurchaseController (
    var purchaseService : PurchaseService  // construtor para injetar dependencias e criar atributo
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase(@RequestBody requestPurchase: PurchaseDTO){
        purchaseService.create(requestPurchase)
    }

}