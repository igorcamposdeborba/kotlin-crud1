package com.igorborba.crud.service.login

import com.igorborba.crud.domain.dto.CustomerDTO
import com.igorborba.crud.domain.dto.login.UserLoginDTO
import com.igorborba.crud.domain.entities.login.Login
import com.igorborba.crud.domain.valueObjects.login.LoginStatus
import com.igorborba.crud.service.CustomerService
import com.igorborba.crud.service.repository.LoginRepository
import com.igorborba.crud.utils.objectMapper
import org.springframework.stereotype.Service

@Service
class LoginService(val loginDatabase : LoginRepository,
                   val customerService: CustomerService
) {

    fun signup(userLoginDTO: UserLoginDTO): Login {
        runCatching {
            customerService.createCustomer(objectMapper.convertValue(userLoginDTO, CustomerDTO::class.java))

            val newUser : Login = objectMapper.convertValue(userLoginDTO, Login::class.java)
            newUser.status = LoginStatus.ATIVO
            return loginDatabase.save(newUser)
        }.getOrThrow()
    }

    fun updateLogin(userLoginDTO: UserLoginDTO): Login {
        return runCatching {
            val userFinded: Login = loginDatabase.findByEmail(userLoginDTO.email)

            if (userFinded != null){
                val userUpdated : Login = Login(userFinded.email, userLoginDTO.password)

                return loginDatabase.save(userUpdated)
            } else {
                throw IllegalArgumentException("Não é possível alterar o usuário")
            }

        }.getOrThrow()
    }

    fun deleteLogin(userLoginDTO: UserLoginDTO): Unit {
        findByEmail(userLoginDTO.email)
        customerService.deleteCustomer(userLoginDTO.email)

        loginDatabase.save(objectMapper.convertValue(userLoginDTO, Login::class.java))
    }

    fun findByEmail(email: String): Login {
        return findLogin(email)
    }

    private fun findLogin(value: String): Login {
        return runCatching { loginDatabase.findByEmail(value) }.getOrThrow()
    }

}