package com.example.library.repository

import com.example.library.model.Book
import com.example.library.model.Composition
import com.example.library.model.Genre
import com.example.library.util.getIntOrNull
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Types

@Repository
class BookRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : BookRepository {

    @Transactional
    override fun create(libraryObject: Book): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            "insert into book (name, isbn, publication_date)" +
                    "values (:name, :isbn, :publication_date)",
            MapSqlParameterSource(
                mapOf(
                    "name" to libraryObject.name,
                    "isbn" to libraryObject.isbn,
                    "publication_date" to libraryObject.publicationDate
                )
            ),
            keyHolder,
            listOf("id").toTypedArray()
        )

        val id = keyHolder.keys?.getValue("id") as Int
        jdbcTemplate.batchUpdate(
            "insert into book_composition (composition_id, book_id) " +
                    "values (:composition_id, :book_id)",
            libraryObject.compositions.map { composition ->
                MapSqlParameterSource()
                    .addValue("composition_id", composition.id, Types.INTEGER)
                    .addValue("book_id", id, Types.INTEGER)
            }.toTypedArray()
        )

        return id
    }

    @Transactional
    override fun update(id: Int, libraryObject: Book) {
        jdbcTemplate.update(
            "update book set " +
                    "name = :name, " +
                    "isbn = :isbn, " +
                    "publication_date = :publication_date " +
                    "where id = :id",
            mapOf(
                "id" to id,
                "name" to libraryObject.name,
                "isbn" to libraryObject.isbn,
                "publication_date" to libraryObject.publicationDate
            )
        )
        jdbcTemplate.update(
            "delete from book_composition where book_id = :id",
            mapOf(
                "id" to id
            )
        )
        jdbcTemplate.batchUpdate(
            "insert into book_composition (composition_id, book_id) " +
                    "values (:composition_id, :book_id)",
            libraryObject.compositions.map { composition ->
                MapSqlParameterSource()
                    .addValue("composition_id", composition.id, Types.INTEGER)
                    .addValue("book_id", id, Types.INTEGER)
            }.toTypedArray()
        )
    }

    override fun deleteById(id: Int) {
        jdbcTemplate.update(
            "delete from book where id = :id",
            mapOf(
                "id" to id
            )
        )
    }

    override fun findById(id: Int): Book? =
        jdbcTemplate.query(
            "SELECT book.id, book.name, book.isbn, book.publication_date, " +
                    "composition.id as composition_id, composition.name as composition_name, " +
                    "genre.id as genre_id, genre.name as genre_name " +
                    "from book " +
                    "left join book_composition on book.id = book_composition.book_id " +
                    "left join composition on book_composition.composition_id = composition.id " +
                    "left join genre on composition.genre_id = genre.id " +
                    "where book.id = :id",
            mapOf(
                "id" to id
            ),
            SINGLE_ROW_MAPPER
        ).firstOrNull()

    override fun getAll(): List<Book> =
        jdbcTemplate.query(
            "select * from book order by id",
            ROW_MAPPER
        )

    private companion object {
        val ROW_MAPPER = RowMapper<Book> { rs, _ ->
            Book(
                id = rs.getIntOrNull("id"),
                name = rs.getString("name"),
                isbn = rs.getString("isbn"),
                publicationDate = rs.getDate("publication_date"),
                compositions = emptyList()
            )
        }
        val SINGLE_ROW_MAPPER = RowMapper { rs, _ ->
            val id = rs.getIntOrNull("id")
            val name = rs.getString("name")
            val isbn = rs.getString("isbn")
            val publicationDate = rs.getDate("publication_date")
            val compositions: MutableList<Composition> = mutableListOf()
            do {
                try {
                    compositions.add(
                        Composition(
                            id = rs.getIntOrNull("composition_id"),
                            name = rs.getString("composition_name"),
                            Genre(
                                id = rs.getInt("genre_id"),
                                name = rs.getString("genre_name")
                            ),
                            emptyList()
                        )
                    )
                } catch (e: NullPointerException) {
                    break
                }
            } while (rs.next())
            Book(
                id = id,
                name = name,
                isbn = isbn,
                publicationDate = publicationDate,
                compositions = compositions
            )
        }
    }

}