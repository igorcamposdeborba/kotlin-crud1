package com.igorborba.crud.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.igorborba.crud.domain.dto.CustomerDTO
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

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

    fun findByEmail(email: String): CustomerDTO {
        return findCustomer(email)
    }

    fun findById(id: Int): CustomerDTO { // id na url: PathVariable
        return findCustomer(id)
    }

    fun createCustomer(customerDTO: CustomerDTO): CustomerDTO {
        return runCatching {
            customers.add(customerDTO)
            return customerDTO
        }.getOrThrow()
    }

    fun updateCustomer(customer: CustomerDTO): CustomerDTO {
        return customers.runCatching {
            filter{ it.email.equals(customer.email) }
            .map{ objectMapper.updateValue(it, customer) }[0]
        }
        .getOrThrow()
    }

    fun deleteCustomer(email: String): Unit {
        customers.removeIf{ it.email == email }
    }

    @Throws(ResponseStatusException::class)
    private fun findCustomer(email: String): CustomerDTO{
        return customers.find { it.email.equals(email, ignoreCase = true) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
    @Throws(ResponseStatusException::class)
    private fun findCustomer(id: Int): CustomerDTO{ // sobrecarga
        return customers.find { it.id.equals(id) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}