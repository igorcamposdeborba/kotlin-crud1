package com.igorborba.crud.service.repository

import com.igorborba.crud.domain.entities.Customer
import com.igorborba.crud.domain.entities.login.Login
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface LoginRepository: JpaRepository<Login, String> { // : significa extends (herança)

    fun findByEmail(email: String?): Login
}