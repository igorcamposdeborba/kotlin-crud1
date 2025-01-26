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

    fun findAllCustomer(name: String?): List<Customer> { // o tipo do dado de entrada e de saída (Customer) -> CustomerDTO devem ser declarados por ser programação funcional
        return if (name.isNullOrBlank()) {
            customersDatabase.findAll()
        } else {
            customersDatabase.findAllByNameContaining(name)
        }
    }

    fun findByEmail(email: String): Customer {
        return findCustomer(email)
    }
    fun findById(id: String?): Customer {
        return findCustomer(id)
    }

    fun createCustomer(customerDTO: CustomerDTO): Customer {
        runCatching {
            return customersDatabase.save(objectMapper.convertValue(customerDTO, Customer::class.java))
        }.getOrThrow()
    }

    fun updateCustomer(customer: CustomerDTO): Customer {
        return runCatching {
            val customerFinded: Customer = customersDatabase.findByEmail(customer.email)
            if (customerFinded != null){
                customer.id = customerFinded.id
                val customerUpdated : Customer = Utils.convertValue(customer, Customer::class.java)
                return customersDatabase.save(customerUpdated)
            } else {
                return Utils.convertValue(customer, Customer::class.java)
            }

        }.getOrThrow()
    }
    fun deleteCustomer(email: String): Unit {
        customersDatabase.delete(Utils.convertValue(findByEmail(email), Customer::class.java))
    }

    private fun findCustomer(value: String?): Customer {
        return runCatching {
            if (value.toString().contains("@")){
                customersDatabase.findByEmail(value)
            } else {
                customersDatabase.findById(value)
            }
        }.getOrThrow()
    }

}