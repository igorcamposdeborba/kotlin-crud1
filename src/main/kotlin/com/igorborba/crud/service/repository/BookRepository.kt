package com.igorborba.crud.service.repository

import com.igorborba.crud.domain.entities.Book
import com.igorborba.crud.domain.entities.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BookRepository: JpaRepository<Book, Int> { // : significa extends (herança)

    fun findAllByNameContaining(name: String?): List<Book>  // Modificado de "findAllByName" para "findByName"

    fun findByName(name: String): Book

    fun findById(id: String): Book
}