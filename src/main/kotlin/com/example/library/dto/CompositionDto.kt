package com.example.library.dto

data class CompositionDto(
    val id: Int? = null,
    val authors: List<AuthorDto>? = null,
    val genre: GenreDto,
    val name: String
)
