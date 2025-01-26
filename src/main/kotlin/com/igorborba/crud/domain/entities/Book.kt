package com.igorborba.crud.domain.entities

import com.igorborba.crud.configs.database.DatabaseConfig
import com.igorborba.crud.domain.valueObjects.BookStatus
import jakarta.persistence.*
import lombok.NoArgsConstructor
import java.math.BigDecimal

@Entity(name = "book")
@NoArgsConstructor
class Book (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = DatabaseConfig.ID_LENGTH_MYSQL, unique = DatabaseConfig.IS_UNIQUE, nullable = DatabaseConfig.IS_NULLABLE)
    var id: String?, // UUID é criado no banco de dados e legível para humanos sendo String
    var name: String,
    var price: BigDecimal,

    @Enumerated(EnumType.STRING)
    var status: BookStatus?,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? // associacao
) {
}