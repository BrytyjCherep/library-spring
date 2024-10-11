package com.example.library.model

import java.sql.Date


data class Book(
    val id: Int? = null,
    val name: String,
    val isbn: String,
    val publicationDate: Date,
    val compositions: List<Composition>
)
