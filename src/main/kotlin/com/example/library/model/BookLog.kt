package com.example.library.model

import java.sql.Date

data class BookLog(
    val id: Int = 0,
    val libraryBookDto: LibraryBook,
    val readerDto: Reader,
    val issueDate: Date,
    val returnDate: Date? = null
)
