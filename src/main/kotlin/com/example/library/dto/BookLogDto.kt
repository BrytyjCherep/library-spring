package com.example.library.dto

import java.sql.Date

data class BookLogDto(
    val id: Int? = null,
    val libraryBook: LibraryBookDto,
    val reader: ReaderDto,
    val issueDate: Date,
    val returnDate: Date? = null
)
