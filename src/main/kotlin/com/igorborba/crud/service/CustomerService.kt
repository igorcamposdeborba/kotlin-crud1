package com.igorborba.crud.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.igorborba.crud.domain.dto.CustomerDTO
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@Service
class CustomerService {
    val objectMapper = jacksonObjectMapper()

    val customers: MutableList<CustomerDTO> = mutableListOf<CustomerDTO>(
        CustomerDTO("Roberto", "roberto@hotmail.com"),
        CustomerDTO( "Igor", "igor@hotmail.com")
    )

    fun findAllCustomer(name: String?): List<CustomerDTO> {
        name?.let { // filtrar por contém parte do nome. // let roda no escopo local. ? acessa se name não for null
            return customers.filter { it.name.contains(name, true) }
        }
        return customers
    }

    @Throws(ResponseStatusException::class)
    fun findByEmail(email: String): CustomerDTO {
        return findCustomer(email)
    }

    @Throws(ResponseStatusException::class)
    fun findById(id: Int): CustomerDTO { // id na url: PathVariable
        return findCustomer(id)
    }

    fun createCustomer(customerDTO: CustomerDTO): CustomerDTO {
        return runCatching {
            customerDTO
        }.getOrThrow()
    }

    fun updateCustomer(customer: CustomerDTO): CustomerDTO? {
        var customerDTO : CustomerDTO = findCustomer(customer.email)

        val updatedCustomer: CustomerDTO? = customer?.let {
            val serializedCustomer = objectMapper.writeValueAsString(it)
            objectMapper.readValue(serializedCustomer, CustomerDTO::class.java)
            // !todo: Atualizar no banco de dados aqui
        }

        return updatedCustomer
    }

    fun deleteCustomer(email: String): Unit {
        customers.removeIf{ it.email == email }
    }

    private fun findCustomer(email: String): CustomerDTO{
        return customers.find { it.email.equals(email, ignoreCase = true) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
    private fun findCustomer(id: Int): CustomerDTO{ // sobrecarga
        return customers.find { it.id.equals(id) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}