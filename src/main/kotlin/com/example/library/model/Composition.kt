package com.example.library.model

data class Composition(
    val id: Int? = null,
    val authors: List<Author>,
    val genre: Genre,
    val name: String
)
