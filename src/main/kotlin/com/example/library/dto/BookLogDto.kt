package com.example.library.dto

import java.sql.Date

data class BookLogDto(
    val id: Int = 0,
    val libraryBookDto: LibraryBookDto,
    val readerDto: ReaderDto,
    val issueDate: Date,
    val returnDate: Date? = null
)
