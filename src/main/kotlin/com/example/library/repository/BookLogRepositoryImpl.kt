package com.example.library.repository

import com.example.library.model.Book
import com.example.library.model.BookLog
import com.example.library.model.LibraryBook
import com.example.library.model.Reader
import com.example.library.util.getIntOrNull
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class BookLogRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : BookLogRepository {
    override fun create(libraryObject: BookLog): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            "insert into book_log (reader_id, library_book_id, issue_date, return_date)" +
                    "values (:reader_id, :library_book_id, :issue_date, :return_date)",
            MapSqlParameterSource(
                mapOf(
                    "reader_id" to libraryObject.reader.id,
                    "library_book_id" to libraryObject.libraryBook.id,
                    "issue_date" to libraryObject.issueDate,
                    "return_date" to libraryObject.returnDate
                )
            ),
            keyHolder,
            listOf("id").toTypedArray()
        )
        return keyHolder.keys?.getValue("id") as Int
    }

    override fun update(id: Int, libraryObject: BookLog) {
        jdbcTemplate.update(
            "update book_log set " +
                    "reader_id = :reader_id, " +
                    "library_book_id = :library_book_id, " +
                    "issue_date = :issue_date, " +
                    "return_date = :return_date " +
                    "where id = :id",
            mapOf(
                "id" to id,
                "reader_id" to libraryObject.reader.id,
                "library_book_id" to libraryObject.libraryBook.id,
                "issue_date" to libraryObject.issueDate,
                "return_date" to libraryObject.returnDate
            )
        )
    }

    override fun deleteById(id: Int) {
        jdbcTemplate.update(
            "delete from book_log where id = :id",
            mapOf(
                "id" to id
            )
        )
    }

    override fun findById(id: Int): BookLog? =
        jdbcTemplate.query(
            "SELECT book_log.id, reader_id, library_book_id, issue_date, return_date, " +
                    "book.id as book_id, book.name as book_name, book.isbn as book_isbn, " +
                    "book.publication_date as book_publication_date, reader.name as reader_name, reader.email as reader_email " +
                    "from book_log " +
                    "join library_book on library_book_id = library_book.id " +
                    "join book on library_book.book_id = book.id " +
                    "join reader on reader_id = reader.id " +
                    "where book_log.id = :id",
            mapOf(
                "id" to id
            ),
            ROW_MAPPER
        ).firstOrNull()

    override fun getAll(): List<BookLog> =
        jdbcTemplate.query(
            "SELECT book_log.id, reader_id, library_book_id, issue_date, return_date, " +
                    "book.id as book_id, book.name as book_name, book.isbn as book_isbn, " +
                    "book.publication_date as book_publication_date, reader.name as reader_name, reader.email as reader_email " +
                    "from book_log " +
                    "join library_book on library_book_id = library_book.id " +
                    "join book on library_book.book_id = book.id " +
                    "join reader on reader_id = reader.id " +
                    "order by book_log.id",
            ROW_MAPPER
        )

    override fun getListOfOverdueReaders(currentDate: LocalDate): List<BookLog> =
        jdbcTemplate.query(
            "SELECT book_log.id, reader_id, library_book_id, issue_date, return_date, " +
                    "book.id as book_id, book.name as book_name, book.isbn as book_isbn, " +
                    "book.publication_date as book_publication_date, reader.name as reader_name, reader.email as reader_email " +
                    "from book_log " +
                    "join library_book on library_book_id = library_book.id " +
                    "join book on library_book.book_id = book.id " +
                    "join reader on reader_id = reader.id " +
                    "where return_date IS NULL AND issue_date < :current_date",
            mapOf(
                "current_date" to currentDate
            ),
            ROW_MAPPER
        )

    override fun getListForReturnBook(isbn: String, readerId: Int): List<BookLog> =
        jdbcTemplate.query(
            "SELECT book_log.id, reader_id, library_book_id, issue_date, return_date, " +
                    "book.id as book_id, book.name as book_name, book.isbn as book_isbn, " +
                    "book.publication_date as book_publication_date, reader.name as reader_name, reader.email as reader_email " +
                    "from book_log " +
                    "join library_book on library_book_id = library_book.id " +
                    "join book on library_book.book_id = book.id " +
                    "join reader on reader_id = reader.id " +
                    "where book.isbn = :isbn AND reader.id = :reader_id AND return_date IS NULL " +
                    "order by issue_date desc",
            mapOf(
                "isbn" to isbn,
                "reader_id" to readerId
            ),
            ROW_MAPPER
        )

    override fun updateReturnDate(bookLogId: Int, returnDate: LocalDate) {
        jdbcTemplate.update(
            "update book_log set " +
                    "return_date = :return_date " +
                    "where id = :id",
            mapOf(
                "return_date" to returnDate,
                "id" to bookLogId
            )
        )
    }

    private companion object {
        val ROW_MAPPER = RowMapper<BookLog> { rs, _ ->
            BookLog(
                id = rs.getIntOrNull("id"),
                libraryBook = LibraryBook(
                    rs.getIntOrNull("library_book_id"),
                    book = Book(
                        rs.getIntOrNull("book_id"),
                        rs.getString("book_name"),
                        rs.getString("book_isbn"),
                        rs.getDate("book_publication_date"),
                        emptyList()
                    )
                ),
                reader = Reader(
                    id = rs.getIntOrNull("reader_id"),
                    name = rs.getString("reader_name"),
                    email = rs.getString("reader_email")
                ),
                issueDate = rs.getDate("issue_date"),
                returnDate = rs.getDate("return_date")
            )
        }
    }
}