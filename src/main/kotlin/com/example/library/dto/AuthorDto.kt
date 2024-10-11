package com.example.library.dto

import java.sql.Date

data class AuthorDto(
    val id: Int? = null,
    val name: String,
    val nickname: String? = null,
    val birthDate: Date
)
