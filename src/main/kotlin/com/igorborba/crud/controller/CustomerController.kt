package com.igorborba.crud.controller

import com.igorborba.crud.domain.dto.CustomerDTO
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.Optional
import java.util.UUID

@RestController
@RequestMapping("/customer")
class CustomerController {
    // !todo: implement model for this class

    @GetMapping(path = ["/", ""])
    fun findAllCustomer(): ResponseEntity<CustomerDTO>{
        return ResponseEntity.ok().body(CustomerDTO(UUID.randomUUID(), "Igor", "igor@hotmail.com"))
    }

    @Throws(ResponseStatusException::class)
    @PostMapping("/email")
    fun findByEmail(@RequestBody email: String): ResponseEntity<CustomerDTO> {
        val customer = listOf(CustomerDTO(UUID.randomUUID(), "Roberto", "roberto@hotmail.com"),
                              CustomerDTO(UUID.randomUUID(), "Igor", "igor@hotmail.com")
                             ).find { it.email.equals(email, ignoreCase = true) }
                             ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

        return ResponseEntity.ok().body(customer)
    }

    @PostMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@Valid @RequestBody customerDTO: CustomerDTO): ResponseEntity<CustomerDTO?> {

        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customerDTO).toUri()
        return runCatching {
            ResponseEntity.created(uri).body(customerDTO)
        }.getOrThrow()
    }
}
