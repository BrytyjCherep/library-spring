package com.example.library.repository

import com.example.library.model.BookLog
import java.time.LocalDate

interface BookLogRepository : LibraryRepository<BookLog> {
    fun getListOfOverdueReaders(currentDate: LocalDate): List<BookLog>
    fun updateReturnDate(bookLogId: Int, returnDate: LocalDate)
    fun getListForReturnBook(isbn: String, readerId: Int): List<BookLog>
    fun makeReport(libraryBookId: Int, readerId: Int): List<BookLog>
}