package com.example.library.dto

import java.util.*

data class BookDto(
    val id: Int? = null,
    val name: String,
    val isbn: String,
    val publicationDate: Date,
    val compositions: List<CompositionDto>
)
