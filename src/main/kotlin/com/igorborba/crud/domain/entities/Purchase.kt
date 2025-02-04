package com.igorborba.crud.domain.entities

import com.igorborba.crud.domain.dto.CustomerDTO
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(name = "purchase")
class Purchase(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String?,

    // Muitas compras (purchase) para 1 cliente (customer)
    @ManyToOne
    @JoinColumn(name = "customer_id") // coluna
    val customer: Customer,

    // O mesmo livro pode estar em várias compras (purchase) = many to many. Olhando da perspectiva de Purchase
    @ManyToMany
    @JoinTable(
        name = "purchase_book", // tabela: nome da tabela,
        joinColumns = [JoinColumn(name = "purchase_id")], // PK da entity principal
        inverseJoinColumns = [JoinColumn(name = "book_id")]) // PK da entity secundária
    val books: List<Book>,

    val nfe: String?,
    val totalPrice: BigDecimal,
    val createdAt: LocalDateTime
) {
}