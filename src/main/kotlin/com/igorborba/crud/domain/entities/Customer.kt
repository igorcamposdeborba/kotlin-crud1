package com.igorborba.crud.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.igorborba.crud.configs.database.DatabaseConfig
import com.igorborba.crud.domain.valueObjects.CustomerStatus
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import lombok.NoArgsConstructor

@Entity(name = "customer")
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Customer (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = DatabaseConfig.ID_LENGTH_MYSQL, unique = DatabaseConfig.IS_UNIQUE, nullable = DatabaseConfig.IS_NULLABLE)
    var id: String?, // UUID é criado no banco de dados e legível para humanos sendo String
    var name: String?,
    var email: String?,
    var cpf: String?,
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus?
) {
}
