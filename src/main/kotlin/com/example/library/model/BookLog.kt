package com.example.library.model

import java.sql.Date

data class BookLog(
    val id: Int? = null,
    val libraryBook: LibraryBook,
    val reader: Reader,
    val issueDate: Date,
    val returnDate: Date? = null
)
