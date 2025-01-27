package com.igorborba.crud.service

import com.igorborba.crud.domain.dto.CustomerDTO
import com.igorborba.crud.domain.entities.Book
import com.igorborba.crud.domain.entities.Customer
import com.igorborba.crud.domain.valueObjects.BookStatus
import com.igorborba.crud.domain.valueObjects.CustomerStatus
import com.igorborba.crud.service.repository.BookRepository
import com.igorborba.crud.service.repository.CustomerRepository
import com.igorborba.crud.utils.Utils
import com.igorborba.crud.utils.objectMapper
import org.springframework.stereotype.Service

@Service
class CustomerService (val customersDatabase : CustomerRepository,
                       val bookDatabase: BookRepository) {

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
            customerDTO.status = CustomerStatus.ATIVO
            return customersDatabase.save(objectMapper.convertValue(customerDTO, Customer::class.java))
        }.getOrThrow()
    }

    fun updateCustomer(customer: CustomerDTO): Customer {
        return runCatching {
            val customerFinded: Customer = customersDatabase.findByEmail(customer.email)

            if (customerFinded != null && customerFinded.status != CustomerStatus.DELETADO && customerFinded.status != CustomerStatus.DESATIVADO){
                customer.id = customerFinded.id
                customer.status = if (customer.status != null) customer.status else customerFinded.status
                val customerUpdated : Customer = Utils.convertValue(customer, Customer::class.java)
                return customersDatabase.save(customerUpdated)
            } else {
                throw IllegalArgumentException("Não é possível alterar quando o status é ${customerFinded.status}")
            }

        }.getOrThrow()
    }
    fun deleteCustomer(email: String): Unit {
        val customer : Customer = findByEmail(email)

        deleteBook(customer) // DELETE LÓGICO (muda status para deletado)
        customer.status = CustomerStatus.DELETADO
        customersDatabase.save(customer)

//      customersDatabase.delete(Utils.convertValue(customer, Customer::class.java)) // deleção persistente (caso seja implementado, mudar o deleteBook(customer) para deletar do banco de dados os livros)
    }
    private fun deleteBook(customer: Customer) {
        val books: List<Book> = bookDatabase.findByCustomerId(customer.id.toString())
        books.forEach{ it.status = BookStatus.DELETADO }

        bookDatabase.saveAll(books)
//        bookDatabase.deleteAll(books) // deleção persistente (caso seja implementado, descomentar para deletar do banco de dados os livros)
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