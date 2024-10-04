package com.example.library.model

import java.util.*

data class Author(
    val id: Int? = null,
    val name: String,
    val nickname: String? = null,
    val birthDate: Date
)
