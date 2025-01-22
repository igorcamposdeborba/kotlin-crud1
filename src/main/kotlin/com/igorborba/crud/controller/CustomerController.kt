package com.igorborba.crud.controller

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

    @GetMapping(path = ["/", ""])
    fun findAllCustomer(): ResponseEntity<CustomerDTO>{
        return ResponseEntity.ok().body(CustomerDTO("Igor", "igor@hotmail.com"))
    }

    @Throws(ResponseStatusException::class)
    @PostMapping("/email")
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
    fun updateCustomer(@Valid @RequestBody customer: CustomerDTO): ResponseEntity<CustomerDTO>{
        val customerDTO : CustomerDTO = findCustomer(customer.email)

        customerDTO.let {
            if (it != null){
                objectMapper.readValue<CustomerDTO>(it.toString())
            }
        }

        return ResponseEntity.ok().body(customer)
    }

    private fun findCustomer(email: String): CustomerDTO{
        return listOf(CustomerDTO("Roberto", "roberto@hotmail.com"),
            CustomerDTO( "Igor", "igor@hotmail.com")
        ).find { it.email.equals(email, ignoreCase = true) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}
