package com.igorborba.crud.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.igorborba.crud.domain.valueObjects.BookStatus
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import java.math.BigDecimal

@Entity(name = "book")
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Book (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?, // UUID é criado no banco de dados e legível para humanos sendo String
    var title: String,
    var price: BigDecimal,
    @Enumerated(EnumType.STRING)
    var status: BookStatus?,

//     @ManyToOne
    @JoinColumn(name = "customer_id")
    var customerId: String?, // associacao

) {
}