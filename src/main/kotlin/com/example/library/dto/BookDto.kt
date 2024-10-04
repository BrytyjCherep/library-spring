package com.example.library.dto

import java.sql.Date

data class BookDto(
    val id: Int = 0,
    val name: String,
    val isbn: String,
    val publicationDate: Date,
    val compositions: List<CompositionDto>
)
