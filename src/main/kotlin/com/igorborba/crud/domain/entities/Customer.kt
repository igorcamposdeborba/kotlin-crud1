package com.igorborba.crud.domain.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import java.util.UUID


@Entity(name = "customer")
class Customer(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,
    var name: String,
    var email: String,
    var cpf: String
) {
}