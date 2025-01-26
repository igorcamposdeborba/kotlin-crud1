package com.igorborba.crud.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.convertValue
import com.igorborba.crud.domain.dto.CustomerDTO
import com.igorborba.crud.domain.entities.Customer
import com.igorborba.crud.service.CustomerService
import com.igorborba.crud.utils.Utils
import com.igorborba.crud.utils.objectMapper
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.Collections

@RestController
@RequestMapping("/customer")
class CustomerController (
    val customerService : CustomerService // construtor para injetar dependencias e criar atributo
) {

    @GetMapping(path = ["/", ""]) // parametro de UTM: RequestParam
    fun findAllCustomer(@RequestParam name: String?): ResponseEntity<List<CustomerDTO>> {
        return ResponseEntity.ok().body(convertToCustomerCollectionDTO(customerService.findAllCustomer(name)))
    }

    @PostMapping("/email")
    @Throws(ResponseStatusException::class)
    fun findByEmail(@RequestBody email: String): ResponseEntity<CustomerDTO> {
        return ResponseEntity.ok().body(convertToCustomerDTO(customerService.findByEmail(email)))
    }

    @GetMapping("/{id}")
    @Throws(ResponseStatusException::class)
    fun findById(@PathVariable id: String): ResponseEntity<CustomerDTO> {
        return ResponseEntity.ok().body(convertToCustomerDTO(customerService.findById(id)))
    }

    @PostMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    fun createCustomer(@Valid @RequestBody customerDTO: CustomerDTO): ResponseEntity<CustomerDTO?> {
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customerDTO).toUri()

        return runCatching {
            ResponseEntity.created(uri).body(convertToCustomerDTO(customerService.createCustomer(customerDTO)))
        }.getOrThrow()
    }

    @PutMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    fun updateCustomer(@RequestBody customer: CustomerDTO): ResponseEntity<CustomerDTO>{
        return ResponseEntity.ok().body(convertToCustomerDTO(customerService.updateCustomer(customer)))
    }

    @DeleteMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun deleteCustomer(@RequestBody email: String): Unit {
        customerService.deleteCustomer(email)
    }

    private val convertToCustomerDTO : (Customer) -> CustomerDTO = { it -> // não é possível criar com bloco de código no Kotlin porque ele sempre retorna a última linha por ser programação funcional
        objectMapper.convertValue(it, CustomerDTO::class.java)
    }

    private val convertToCustomerCollectionDTO : (List<Customer>) -> List<CustomerDTO> = { it ->
        objectMapper.convertValue(it, object: TypeReference<List<CustomerDTO>>(){})
    }
}
