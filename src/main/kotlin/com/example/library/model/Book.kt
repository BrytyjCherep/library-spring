package com.example.library.model

import java.util.*


data class Book(
    val id: Int? = null,
    val name: String,
    val isbn: String,
    val publicationDate: Date,
    val compositions: List<Composition>
)
