package com.igorborba.crud.domain.exceptions.validation

import com.igorborba.crud.domain.exceptions.exceptionsMessages.ExceptionAttributes
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD) // onde vai ser usado: em atributo
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EmailAvailableValidator::class])
annotation class EmailAvailable(
    val message: String = ExceptionAttributes.EMAIL_REGISTERED,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
