package com.igorborba.crud.service.repository

import com.igorborba.crud.domain.entities.Book
import com.igorborba.crud.domain.entities.Customer
import com.igorborba.crud.domain.valueObjects.BookStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BookRepository: JpaRepository<Book, Int> { // : significa extends (herança)

    fun findByNameContaining(name: String): List<Book>

    fun findByName(name: String): Book

    fun findByStatus(status: BookStatus): List<Book>

    fun findById(id: String): Book
}