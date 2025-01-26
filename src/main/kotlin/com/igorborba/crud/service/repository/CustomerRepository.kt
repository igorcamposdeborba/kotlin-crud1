package com.igorborba.crud.service.repository

import com.igorborba.crud.domain.entities.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CustomerRepository: JpaRepository<Customer, UUID> { // : significa extends (herança)

    fun findAllByNameContaining(name: String?): List<Customer>  // Modificado de "findAllByName" para "findByName"

    fun findByEmail(email: String?): Customer

    fun findById(id: String?): Customer
}