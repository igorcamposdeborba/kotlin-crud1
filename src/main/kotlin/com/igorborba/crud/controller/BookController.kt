package com.igorborba.crud.controller

import com.igorborba.crud.domain.dto.BookDTO
import com.igorborba.crud.domain.dto.mounted.CustomerBookDTO
import com.igorborba.crud.service.BookService
import com.igorborba.crud.utils.Utils
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/book")
class BookController (
    val bookService : BookService // construtor para injetar dependencias e criar atributo
) {

    @GetMapping(path = ["/", ""]) // parametro de UTM: RequestParam
    fun findAllBooks(@RequestParam name: String?): ResponseEntity<List<BookDTO>> {
        return ResponseEntity.ok().body(bookService.findAllBooks(name))
    }

    @GetMapping(path = ["/customer", "/customer/"])
    @Throws(ResponseStatusException::class)
    fun findCustomerByName(@RequestParam name: String): ResponseEntity<List<CustomerBookDTO>> {
        return ResponseEntity.ok().body(bookService.findCustomerByName(name))
    }

    @GetMapping("/status", "/status/") // return implícito ao atribuir ao métod
    fun findByStatus(@RequestParam status: String): ResponseEntity<List<BookDTO>> =
        ResponseEntity.ok().body(bookService.findByStatus(status))


    @GetMapping("/{id}")
    @Throws(ResponseStatusException::class)
    fun findById(@PathVariable id: Int): ResponseEntity<BookDTO> {
        return ResponseEntity.ok().body(Utils.convertValue(bookService.findById(id), BookDTO::class.java))
    }

    @PostMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@Valid @RequestBody bookDTO: BookDTO): ResponseEntity<BookDTO?> {
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bookDTO).toUri()

        return runCatching {
            ResponseEntity.created(uri).body(bookService.createBook(bookDTO))
        }.getOrThrow()
    }

    @PutMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.OK)
    fun updateBook(@RequestBody book: BookDTO): ResponseEntity<BookDTO>{
        return ResponseEntity.ok().body(bookService.updateBook(book))
    }

    @DeleteMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBookByName(@RequestBody name: String): Unit {
        bookService.deleteByName(name)
    }

    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBookByID(@PathVariable id: Int): Unit {
        bookService.deleteById(id)
    }
}
