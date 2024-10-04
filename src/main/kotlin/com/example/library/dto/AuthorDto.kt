package com.example.library.dto

import java.util.Date

data class AuthorDto(
    val id: Int = 0,
    val name: String,
    val nickname: String? = null,
    val birthDate: Date
)
