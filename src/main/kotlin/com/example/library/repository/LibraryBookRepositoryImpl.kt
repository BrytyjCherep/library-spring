package com.example.library.repository

import com.example.library.model.Book
import com.example.library.model.LibraryBook
import com.example.library.util.getIntOrNull
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class LibraryBookRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : LibraryBookRepository {
    override fun create(libraryObject: LibraryBook): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            "insert into library_book (book_id) values (:book_id)",
            MapSqlParameterSource(
                mapOf(
                    "book_id" to libraryObject.book.id,
                )
            ),
            keyHolder,
            listOf("id").toTypedArray()
        )
        return keyHolder.keys?.getValue("id") as Int
    }

    override fun update(id: Int, libraryObject: LibraryBook) {
        jdbcTemplate.update(
            "update library_book set book_id = :book_id where id = :id",
            mapOf(
                "id" to id,
                "book_id" to libraryObject.book.id,
            )
        )
    }

    override fun deleteById(id: Int) {
        jdbcTemplate.update(
            "delete from library_book where id = :id",
            mapOf(
                "id" to id
            )
        )
    }

    override fun findById(id: Int): LibraryBook? =
        jdbcTemplate.query(
            "select library_book.id, book.id as book_id, book.name, book.isbn, book.publication_date " +
                    "from library_book " +
                    "JOIN book ON library_book.book_id = book.id " +
                    "where library_book.id = :id",
            mapOf(
                "id" to id
            ),
            ROW_MAPPER
        ).firstOrNull()


    override fun getAll(): List<LibraryBook> =
        jdbcTemplate.query(
            "select library_book.id, book.id as book_id, book.name, book.isbn, book.publication_date " +
                    "from library_book " +
                    "JOIN book ON library_book.book_id = book.id " +
                    "order by library_book.id",
            ROW_MAPPER
        )

    private companion object {
        val ROW_MAPPER = RowMapper<LibraryBook> { rs, _ ->
            LibraryBook(
                id = rs.getIntOrNull("id"),
                book = Book(
                    rs.getIntOrNull("book_id"),
                    rs.getString("name"),
                    rs.getString("isbn"),
                    rs.getDate("publication_date"),
                    emptyList()
                )
            )
        }
    }
}