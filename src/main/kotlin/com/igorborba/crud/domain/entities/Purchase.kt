package com.igorborba.crud.domain.entities

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
data class Purchase(

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
    val books: MutableList<Book>, // MutableList é mutável (List é imutável). Então ela permite remover/alterar os atributo de um objeto já criado anteriormente. Isso é útil aqui para depois de intanciado o objeto, alterar este atributo

    val nfe: String?,
    val totalPrice: BigDecimal,
    val createdAt: LocalDateTime = LocalDateTime.now()
){
    constructor(
        customer: Customer,
        books: MutableList<Book>,
        totalPrice: BigDecimal
    ) : this(
        id = null,
        customer = customer,
        books = books,
        nfe = null,
        totalPrice = totalPrice,
        createdAt = LocalDateTime.now()
    )
}