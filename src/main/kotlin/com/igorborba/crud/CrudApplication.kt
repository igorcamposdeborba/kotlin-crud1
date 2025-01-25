package com.igorborba.crud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class CrudApplication

fun main(args: Array<String>) {
	runApplication<CrudApplication>(*args)
}
