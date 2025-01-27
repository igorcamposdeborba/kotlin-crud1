package com.igorborba.crud.service.repository

import com.igorborba.crud.domain.entities.Book
import com.igorborba.crud.domain.valueObjects.BookStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: JpaRepository<Book, Int> { // : significa extends (herança)

    fun findByTitleContaining(title: String, pageable: Pageable): Page<Book>

    fun findByTitle(title: String): Book

    fun findByStatus(status: BookStatus): List<Book>

    fun findById(id: String): Book

    fun findByCustomerId(id: String): List<Book>
}