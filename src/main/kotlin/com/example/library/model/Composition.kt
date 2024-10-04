package com.example.library.model

import com.example.library.dto.GenreDto

data class Composition(
    val id: Int = 0,
    val authors: List<Author>,
    val genre: GenreDto,
    val name: String
)
