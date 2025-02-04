package com.igorborba.crud.controller

import com.igorborba.crud.domain.dto.PurchaseDTO
import com.igorborba.crud.service.CustomerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class PurchaseController (
    var customerService : CustomerService  // construtor para injetar dependencias e criar atributo
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase(@RequestBody requestPurchase: PurchaseDTO){

    }

}