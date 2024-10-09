package com.example.library.model

data class Composition(
    val id: Int? = null,
    val name: String,
    val genre: Genre,
    val authors: List<Author>
)
