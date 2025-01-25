package com.igorborba.crud.service

import com.igorborba.crud.domain.dto.CustomerDTO
import com.igorborba.crud.domain.entities.Customer
import com.igorborba.crud.service.repository.CustomerRepository
import com.igorborba.crud.utils.Utils
import com.igorborba.crud.utils.objectMapper
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import java.util.Objects
import java.util.UUID

@Service
class CustomerService (val customersDatabase : CustomerRepository) {

    val convertToCustomerDTO : (Customer) -> CustomerDTO = { it -> // não é possível criar com bloco de código no Kotlin porque ele sempre retorna a última linha por ser programação funcional
        objectMapper.convertValue(it, CustomerDTO::class.java)
    }

    fun findAllCustomer(name: String?): List<CustomerDTO> { // o tipo do dado de entrada e de saída (Customer) -> CustomerDTO devem ser declarados por ser programação funcional
        return if (name.isNullOrBlank()) {
            customersDatabase.findAll().map(convertToCustomerDTO)
        } else {
            customersDatabase.findAllByNameContaining(name).map(convertToCustomerDTO)
        }
    }

    fun findByEmail(email: String): CustomerDTO {
        return findCustomer(email)
    }
    fun findById(id: String): CustomerDTO {
        return findCustomer(id)
    }

    fun createCustomer(customerDTO: CustomerDTO): CustomerDTO {
        runCatching {
            val customerSaved : Customer = customersDatabase.save(objectMapper.convertValue(customerDTO, Customer::class.java))
            return objectMapper.convertValue(customerSaved, customerDTO::class.java)
        }.getOrThrow()
    }

    fun updateCustomer(customer: CustomerDTO): CustomerDTO {
//        return runCatching {
//            val customerFinded : Customer = customersDatabase.findByEmail(customer.email)
//            if (customerFinded != null){
//                val customerConverted : Customer= Utils.convertValue(customer, Customer::class.java)
//                val customerUpdated: Customer = customersDatabase.save(customerConverted)
//                return Utils.convertValue(customerUpdated, CustomerDTO::class.java)
//            } else {
//                return throw NotFoundException()
//            }
//        }.getOrThrow()
        return runCatching {
            val customerFinded : Customer = Utils.convertValue(customersDatabase.findByEmail(customer.email), Customer::class.java)
            val customerUpdated: Customer = customersDatabase.save(customerFinded)
            return Utils.convertValue(customerUpdated, CustomerDTO::class.java)

        }.getOrThrow()
    }
    fun deleteCustomer(email: String): Unit {
        customersDatabase.delete(Utils.convertValue(findByEmail(email), Customer::class.java))
    }
//    fun updateCustomer(customer: CustomerDTO): CustomerDTO {
//        return customersDatabase.runCatching {
//            filter{ it.email.equals(customer.email) }         // !todo: equals deixa nullPointerException acontecer. Se fosse aceitar o lint == não daria exception porque colocaria o objeto como null e nunca cairia no throw
//            .map{ objectMapper.updateValue(it, customer) }[0] // adicional 1: Java x Kotlin: é completamente diferente de Java: == em java compara endereços de memória que em kotlin compara o valor.
//        }                                                     //                             equals() em java compara o valor desde que tenha um @override implementado do equals
//                                                              //                             (lombok sobrescreve o equals com todos os atributos por padrão)
//        .getOrThrow()                                         // adicional 2: JavaScript x Kotlin: em Kotlin === compara sempre endereço de memória ou tipo de dado para tipos primitivos
//    }                                                         //                             (diferente do JavaScript que === compara tipo e valor)

    private fun findCustomer(value: String): CustomerDTO {
        return runCatching {
            if (value.contains("@")){
                Utils.convertValue(customersDatabase.findByEmail(value), CustomerDTO::class.java)
            } else {
                Utils.convertValue(customersDatabase.findById(value), CustomerDTO::class.java)
            }
        }.getOrThrow()
    }

}