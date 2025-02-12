package com.igorborba.crud.domain.entities.login

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.igorborba.crud.domain.valueObjects.login.LoginStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@Entity(name = "login")
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Login (
    @Id
    val email: String,
    var password: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Enumerated(EnumType.STRING)
    var status: LoginStatus? = LoginStatus.ATIVO
}