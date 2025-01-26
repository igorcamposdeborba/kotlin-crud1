package com.igorborba.crud.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.igorborba.crud.configs.database.DatabaseConfig
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.NoArgsConstructor

@Entity(name = "customer")
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Customer (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = DatabaseConfig.ID_LENGTH_MYSQL, unique = DatabaseConfig.IS_UNIQUE, nullable = DatabaseConfig.IS_NULLABLE)
    var id: String?, // UUID é criado no banco de dados e legível para humanos sendo String
    var name: String,
    var email: String?,
    var cpf: String?
) {
}
