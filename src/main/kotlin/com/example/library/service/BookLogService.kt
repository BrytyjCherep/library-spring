package com.example.library.service

import com.example.library.dto.BookLogDto
import java.time.LocalDate

interface BookLogService : LibraryService<BookLogDto> {
    fun returnBook(id:Int, returnDate: LocalDate)
}