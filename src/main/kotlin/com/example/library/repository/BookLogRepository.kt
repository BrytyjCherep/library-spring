package com.example.library.repository

import com.example.library.model.BookLog
import java.time.LocalDate

interface BookLogRepository : LibraryRepository<BookLog> {
    fun getListOfOverdueReaders(currentDate: LocalDate): List<BookLog>
}