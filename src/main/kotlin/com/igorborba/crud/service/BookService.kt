package com.igorborba.crud.service

import com.igorborba.crud.domain.dto.BookDTO
import com.igorborba.crud.domain.entities.Book
import com.igorborba.crud.domain.entities.Customer
import com.igorborba.crud.domain.valueObjects.BookStatus
import com.igorborba.crud.service.repository.BookRepository
import com.igorborba.crud.utils.Utils
import com.igorborba.crud.utils.objectMapper
import org.springframework.stereotype.Service

@Service
class BookService (val bookDatabase : BookRepository,
                   val customerService: CustomerService) {

    val convertToBookDTO : (Book) -> BookDTO = { it -> // não é possível criar com bloco de código no Kotlin porque ele sempre retorna a última linha por ser programação funcional
        objectMapper.convertValue(it, BookDTO::class.java)
    }

    fun findAllBooks(name: String?): List<BookDTO> { // o tipo do dado de entrada e de saída (Book) -> BookDTO devem ser declarados por ser programação funcional
        return if (name.isNullOrBlank()) {
            bookDatabase.findAll().map(convertToBookDTO)
        } else {
            bookDatabase.findByNameContaining(name).map(convertToBookDTO)
        }
    }

    fun findByStatus(status: String): List<BookDTO> {
        return bookDatabase.findByStatus(BookStatus.valueOf(status.uppercase())).map(convertToBookDTO)
    }

    fun findByName(name: String): BookDTO {
        return findBook(name)
    }
    fun findById(id: Int): Book {
        return bookDatabase.findById(id).orElseThrow()
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
            val bookFinded: Book = if (book.id != null) bookDatabase.findById(book.id!!).orElseThrow() else bookDatabase.findByName(book.name)
            if (bookFinded != null){
                book.id = if (book.id != null) book.id else bookFinded.id
                book.status = if (book.status != null) book.status else bookFinded.status
                book.customerId = bookFinded.customerId
                val bookUpdated : Book = Utils.convertValue(book, Book::class.java)
                return Utils.convertValue(bookDatabase.save(bookUpdated), BookDTO::class.java)
            } else {
                return book
            }

        }.getOrThrow()
    }
    fun deleteByName(name: String): Unit {
        bookDatabase.delete(Utils.convertValue(findByName(name), Book::class.java))
    }

    fun deleteById(id: Int): Unit {
        bookDatabase.delete(bookDatabase.findById(id).orElseThrow())
    }

    private fun findBook(value: String): BookDTO {
        return runCatching {
            Utils.convertValue(bookDatabase.findByName(value), BookDTO::class.java)
        }.getOrThrow()
    }

}