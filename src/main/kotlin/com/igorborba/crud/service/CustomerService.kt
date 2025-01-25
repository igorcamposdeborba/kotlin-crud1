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

    val customersDatabase: MutableList<CustomerDTO> = mutableListOf<CustomerDTO>(
        CustomerDTO("Roberto", "roberto@hotmail.com"),
        CustomerDTO( "Igor", "igor@hotmail.com")
    )

    fun findAllCustomer(name: String?): List<CustomerDTO> {
        name?.let { // filtrar por contém parte do nome. // let roda no escopo local. ? acessa se name não for null
            return customersDatabase.filter { it.name.contains(name, true) }
        }
        return customersDatabase
    }

    fun findByEmail(email: String): CustomerDTO {
        return findCustomer(email)
    }

    fun findById(id: Int): CustomerDTO {
        return findCustomer(id)
    }

    fun createCustomer(customerDTO: CustomerDTO): CustomerDTO {
        return runCatching {
            customersDatabase.add(customerDTO)
            return customerDTO
        }.getOrThrow()
    }

    fun updateCustomer(customer: CustomerDTO): CustomerDTO {
        return customersDatabase.runCatching {
            filter{ it.email.equals(customer.email) }         // !todo: equals deixa nullPointerException acontecer. Se fosse aceitar o lint == não daria exception porque colocaria o objeto como null e nunca cairia no throw
            .map{ objectMapper.updateValue(it, customer) }[0] // adicional 1: Java x Kotlin: é completamente diferente de Java: == em java compara endereços de memória que em kotlin compara o valor.
        }                                                     //                             equals() em java compara o valor desde que tenha um @override implementado do equals
                                                              //                             (lombok sobrescreve o equals com todos os atributos por padrão)
        .getOrThrow()                                         // adicional 2: JavaScript x Kotlin: em Kotlin === compara sempre endereço de memória ou tipo de dado para tipos primitivos
    }                                                         //                             (diferente do JavaScript que === compara tipo e valor)

    fun deleteCustomer(email: String): Unit {
        customersDatabase.removeIf{ it.email == email }
    }

    @Throws(ResponseStatusException::class)
    private fun findCustomer(email: String): CustomerDTO{
        return customersDatabase.find { it.email.equals(email, ignoreCase = true) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
    @Throws(ResponseStatusException::class)
    private fun findCustomer(id: Int): CustomerDTO{ // sobrecarga
        return customersDatabase.find { it.id.equals(id) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}