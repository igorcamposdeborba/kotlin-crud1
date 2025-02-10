package com.igorborba.crud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableJpaRepositories
@EnableAsync // !Todo: habilitar eventos assíncronos
class CrudApplication

fun main(args: Array<String>) {
	runApplication<CrudApplication>(*args)
}
