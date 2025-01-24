package com.igorborba.crud.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.igorborba.crud.domain.dto.CustomerDTO
import com.igorborba.crud.service.CustomerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/customer")
class CustomerController (
    val customerService : CustomerService // construtor para injetar dependencias e criar atributo
) {
    
    @GetMapping(path = ["/", ""]) // parametro de UTM: RequestParam
    fun findAllCustomer(@RequestParam name: String?): ResponseEntity<List<CustomerDTO>> {
        return ResponseEntity.ok().body(customerService.findAllCustomer(name))
    }

    @PostMapping("/email")
    @Throws(ResponseStatusException::class)
    fun findByEmail(@RequestBody email: String): ResponseEntity<CustomerDTO> {
        return ResponseEntity.ok().body(customerService.findByEmail(email))
    }

    @PostMapping(name = "/id/")
    @Throws(ResponseStatusException::class)
    fun findById(@PathVariable id: Int): ResponseEntity<CustomerDTO> { // id na url: PathVariable
        return ResponseEntity.ok().body(customerService.findById(id))
    }

    @PostMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@Valid @RequestBody customerDTO: CustomerDTO): ResponseEntity<CustomerDTO?> {

        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customerDTO).toUri()
        return runCatching {
            ResponseEntity.created(uri).body(customerService.createCustomer(customerDTO))
        }.getOrThrow()
    }

    @PutMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.OK)
    fun updateCustomer(@RequestBody customer: CustomerDTO): ResponseEntity<CustomerDTO>{
        return ResponseEntity.ok().body(customerService.updateCustomer(customer))
    }

    @DeleteMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@RequestBody email: String): Unit {
        customerService.deleteCustomer(email)
    }
}
