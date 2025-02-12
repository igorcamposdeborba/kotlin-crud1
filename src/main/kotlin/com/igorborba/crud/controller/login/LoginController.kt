package com.igorborba.crud.controller.login

import com.fasterxml.jackson.core.type.TypeReference
import com.igorborba.crud.domain.dto.CustomerDTO
import com.igorborba.crud.domain.dto.login.UserLoginDTO
import com.igorborba.crud.domain.entities.Customer
import com.igorborba.crud.domain.entities.login.Login
import com.igorborba.crud.service.login.LoginService
import com.igorborba.crud.utils.objectMapper
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/login")
class LoginController (
    val loginService : LoginService // construtor para injetar dependencias e criar atributo
) {

    @PostMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun signup(@Valid @RequestBody userLoginDTO: UserLoginDTO): Unit {
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userLoginDTO).toUri()

        runCatching {
            ResponseEntity.created(uri).body(loginService.signup(userLoginDTO))
        }.getOrThrow()
    }

    @PutMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    fun updateLogin(@Valid @RequestBody userLoginDTO: UserLoginDTO): ResponseEntity<UserLoginDTO>{
        return ResponseEntity.ok().body(convertToUserDTO(loginService.updateLogin(userLoginDTO)))
    }

    @DeleteMapping(path = ["/", ""])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun deleteLogin(@RequestBody userLoginDTO: UserLoginDTO): Unit {
        loginService.deleteLogin(userLoginDTO)
    }

    private val convertToUserDTO : (Login) -> UserLoginDTO = { it -> // não é possível criar com bloco de código no Kotlin porque ele sempre retorna a última linha por ser programação funcional
        objectMapper.convertValue(it, UserLoginDTO::class.java)
    }

    private val convertToCustomerCollectionDTO : (List<Customer>) -> List<CustomerDTO> = { it ->
        objectMapper.convertValue(it, object: TypeReference<List<CustomerDTO>>(){})
    }
}
