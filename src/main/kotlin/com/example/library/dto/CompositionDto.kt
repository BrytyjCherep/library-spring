package com.example.library.dto

data class CompositionDto(
    val id: Int = 0,
    val authors: List<AuthorDto>,
    val genre: GenreDto,
    val name: String
)
