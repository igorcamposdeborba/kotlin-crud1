package com.igorborba.crud.domain.dto.mounted

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.igorborba.crud.domain.dto.BookDTO
import lombok.NoArgsConstructor

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
data class CustomerBookDTO (
    val name: String,
    val books: List<BookDTO>
){
}