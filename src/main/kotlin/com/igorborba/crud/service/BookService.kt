package com.igorborba.crud.service

import com.igorborba.crud.domain.dto.BookDTO
import com.igorborba.crud.domain.dto.mounted.CustomerBookDTO
import com.igorborba.crud.domain.entities.Book
import com.igorborba.crud.domain.entities.Customer
import com.igorborba.crud.domain.exceptions.exceptionTypes.NotFoundException
import com.igorborba.crud.domain.valueObjects.BookStatus
import com.igorborba.crud.domain.exceptions.exceptionsMessages.ExceptionMessages
import com.igorborba.crud.service.repository.BookRepository
import com.igorborba.crud.utils.Utils
import com.igorborba.crud.utils.objectMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService (val bookDatabase : BookRepository,
                   val customerService: CustomerService) {

    val convertToBookDTO : (Book) -> BookDTO = { it -> // não é possível criar com bloco de código no Kotlin porque ele sempre retorna a última linha por ser programação funcional
        objectMapper.convertValue(it, BookDTO::class.java)
    }

    fun findAllBooks(title: String?, pageable: Pageable): Page<BookDTO> { // o tipo do dado de entrada e de saída (Book) -> BookDTO devem ser declarados por ser programação funcional
        return if (title.isNullOrBlank()) {
            bookDatabase.findAll(pageable).map(convertToBookDTO)
        } else {
            bookDatabase.findByTitleContaining(title, pageable).map(convertToBookDTO)
        }
    }

    fun findByStatus(status: String): List<BookDTO> {
        return bookDatabase.findByStatus(BookStatus.valueOf(status.uppercase())).map(convertToBookDTO)
    }

    fun findById(id: Int): Book {
        return bookDatabase.findById(id)
            .orElseThrow()
    }
    fun findById(ids: Set<Int>): List<Book> {
        return bookDatabase.findAllById(ids).toList()
    }

    fun createBook(bookDTO: BookDTO): BookDTO {
        runCatching {
            bookDTO.status = BookStatus.ATIVO

            var bookEntity : Book = Utils.convertValue(bookDTO, Book::class.java)

            val bookSaved : Book = bookDatabase.save(bookEntity)
            return objectMapper.convertValue(bookSaved, BookDTO::class.java)
        }.getOrThrow()
    }

    fun updateBook(book: BookDTO): BookDTO {
        return runCatching {
            val bookFinded: Book = if (book.id != null) bookDatabase.findById(book.id!!).orElseThrow() else bookDatabase.findByTitle(book.title)

            if (bookFinded != null){
                book.id = if (book.id != null) book.id else bookFinded.id
                book.status = if (book.status != null) book.status else bookFinded.status
                book.customerId = bookFinded.customerId
                val bookUpdated : Book = Utils.convertValue(book, Book::class.java)
                return Utils.convertValue(bookDatabase.save(bookUpdated), BookDTO::class.java)
            } else {
                throw IllegalArgumentException("Não é possível alterar quando o status é ${bookFinded.status.toString().lowercase()}")
            }

        }.getOrThrow()
    }
    fun deleteByName(name: String): Unit {
        bookDatabase.delete(Utils.convertValue(findByTitle(name), Book::class.java))
    }

    fun deleteById(id: Int): Unit {
        bookDatabase.delete(bookDatabase.findById(id).orElseThrow())
    }

    private fun findBook(value: String): BookDTO {
        return runCatching {
            Utils.convertValue(bookDatabase.findByTitle(value), BookDTO::class.java)
        }.getOrThrow()
    }

    private fun findByTitle(value: String): BookDTO {
        return runCatching {
            Utils.convertValue(bookDatabase.findByTitle(value), BookDTO::class.java)
        }.getOrThrow()
    }

    fun findCustomerByName(name: String): List<CustomerBookDTO> {
        return runCatching {
            val customersFilteredByName: List<Customer> = customerService.findAllCustomer(name)

            customersFilteredByName.map { customer ->
                val books: List<Book> = bookDatabase.findByCustomerId(customer.id.toString())
                val booksDTO = books.map { convertToBookDTO(it) }

                CustomerBookDTO(customer.name!!, booksDTO) //!todo: revisar esse !!
            }
        }.getOrThrow()
    }

    fun purchase(books : MutableList<Book>): Unit {
        books.map{
            it.status = BookStatus.VENDIDO
        }
        bookDatabase.saveAll(books)
    }

}