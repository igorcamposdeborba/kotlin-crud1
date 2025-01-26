package com.igorborba.crud.controller

import com.igorborba.crud.domain.dto.BookDTO
import com.igorborba.crud.domain.dto.CustomerDTO
import com.igorborba.crud.service.BookService
import com.igorborba.crud.service.CustomerService
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
    val bookService : BookService, // construtor para injetar dependencias e criar atributo
    val customerService : CustomerService
) {

    @GetMapping(path = ["/", ""]) // parametro de UTM: RequestParam
    fun findAllBooks(@RequestParam name: String?): ResponseEntity<List<BookDTO>> {
        return ResponseEntity.ok().body(bookService.findAllBooks(name))
    }

    @GetMapping("/name/{name}")
    @Throws(ResponseStatusException::class)
    fun findByName(@PathVariable name: String): ResponseEntity<BookDTO> {
        return ResponseEntity.ok().body(bookService.findByName(name))
    }

    @GetMapping("/id/{id}")
    @Throws(ResponseStatusException::class)
    fun findById(@PathVariable id: String): ResponseEntity<BookDTO> {
        return ResponseEntity.ok().body(bookService.findById(id))
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
    fun deleteBook(@RequestBody name: String): Unit {
        bookService.deleteByName(name)
    }
}
