package com.igorborba.crud.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.igorborba.crud.domain.dto.CustomerDTO
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/customer")
class CustomerController {
    // !todo: implement service for this class in all endpoints
    val objectMapper = jacksonObjectMapper()

    val customers: MutableList<CustomerDTO> = mutableListOf<CustomerDTO>(CustomerDTO("Roberto", "roberto@hotmail.com"),
                                                                         CustomerDTO( "Igor", "igor@hotmail.com"))

    @GetMapping(path = ["/", ""]) // parametro de UTM: RequestParam
    fun findAllCustomer(@RequestParam name: String?): ResponseEntity<List<CustomerDTO>>{
        name?.let { // filtrar por contém parte do nome. // let roda no escopo local. ? acessa se name não for null
             return ResponseEntity.ok().body(customers.filter { it.name.contains(name, true) })
        }
        return ResponseEntity.ok().body(customers)
    }

    @PostMapping("/email")
    @Throws(ResponseStatusException::class)
    fun findByEmail(@RequestBody email: String): ResponseEntity<CustomerDTO> {
        val customerDTO : CustomerDTO = findCustomer(email)

        return ResponseEntity.ok().body(customerDTO)
    }

    @PostMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@Valid @RequestBody customerDTO: CustomerDTO): ResponseEntity<CustomerDTO?> {

        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customerDTO).toUri()
        return runCatching {
            ResponseEntity.created(uri).body(customerDTO)
        }.getOrThrow()
    }

    @PutMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.OK)
    fun updateCustomer(@RequestBody customer: CustomerDTO): ResponseEntity<CustomerDTO>{
        var customerDTO : CustomerDTO = findCustomer(customer.email)

        val updatedCustomer: CustomerDTO? = customer?.let {
            val serializedCustomer = objectMapper.writeValueAsString(it)
            objectMapper.readValue(serializedCustomer, CustomerDTO::class.java)
            // !todo: Atualizar no banco de dados aqui
        }

        return ResponseEntity.ok().body(updatedCustomer)
    }

    @DeleteMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@RequestBody email: String): Unit {
        customers.removeIf{ it.email == email }
    }

    private fun findCustomer(email: String): CustomerDTO{
        return customers.find { it.email.equals(email, ignoreCase = true) }
                                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}
