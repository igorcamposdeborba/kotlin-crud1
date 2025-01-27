package com.igorborba.crud.domain.exceptions.validation

import com.igorborba.crud.service.CustomerService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class EmailAvailableValidator (var customerService: CustomerService): ConstraintValidator<EmailAvailable, String>{
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value.isNullOrBlank()) {
            return false
        }
        return customerService.emailAvailable(value)
    }

}