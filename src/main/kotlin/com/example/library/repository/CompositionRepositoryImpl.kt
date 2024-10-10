package com.example.library.repository

import com.example.library.model.*
import com.example.library.util.getIntOrNull
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Types

@Repository
class CompositionRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : CompositionRepository {

    @Transactional
    override fun create(libraryObject: Composition): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            "insert into composition (name, genre_id)" +
                    "values (:name, :genre_id)",
            MapSqlParameterSource(
                mapOf(
                    "name" to libraryObject.name,
                    "genre_id" to libraryObject.genre.id
                )
            ),
            keyHolder,
            listOf("id").toTypedArray()
        )

        val id = keyHolder.keys?.getValue("id") as Int
        jdbcTemplate.batchUpdate(
            "insert into composition_author (composition_id, author_id)" +
                    "values (:composition_id, :author_id)",
            libraryObject.authors.map { author ->
                MapSqlParameterSource()
                    .addValue("composition_id", id, Types.INTEGER)
                    .addValue("author_id", author.id, Types.INTEGER)
            }.toTypedArray()
        )
        return id
    }

    @Transactional
    override fun update(id: Int, libraryObject: Composition) {
        jdbcTemplate.update(
            "update composition set " +
                    "name = :name, " +
                    "genre_id = :genre_id " +
                    "where id = :id",
            mapOf(
                "id" to id,
                "name" to libraryObject.name,
                "genre_id" to libraryObject.genre.id
            )
        )
        jdbcTemplate.update(
            "delete from composition_author where composition_id = :id",
            mapOf(
                "id" to id
            )
        )
        jdbcTemplate.batchUpdate(
            "insert into composition_author (composition_id, author_id)" +
                    "values (:composition_id, :author_id)",
            libraryObject.authors.map { author ->
                MapSqlParameterSource()
                    .addValue("composition_id", id, Types.INTEGER)
                    .addValue("author_id", author.id, Types.INTEGER)
            }.toTypedArray()
        )
    }

    override fun deleteById(id: Int) {
        jdbcTemplate.update(
            "delete from composition where id = :id",
            mapOf(
                "id" to id
            )
        )
    }

    override fun findById(id: Int): Composition? =
        jdbcTemplate.query(
            "SELECT composition.id, composition.name, composition.genre_id, genre.name as genre_name, " +
                    "author.id as author_id, author.name as author_name, author.nickname as author_nickname, author.birth_date as author_birth_date " +
                    "from composition " +
                    "left join genre on composition.genre_id = genre.id " +
                    "left join composition_author on composition.id = composition_author.composition_id " +
                    "left join author on composition_author.author_id = author.id " +
                    "where composition.id = :id",
            mapOf(
                "id" to id
            ),
            SINGLE_ROW_MAPPER
        ).firstOrNull()

    override fun getAll(): List<Composition> =
        jdbcTemplate.query(
            "SELECT composition.id, composition.name, composition.genre_id, genre.name as genre_name " +
                    "from composition " +
                    "join genre on composition.genre_id = genre.id",
            ROW_MAPPER
        )

    private companion object {
        val ROW_MAPPER = RowMapper<Composition> { rs, _ ->
            Composition(
                id = rs.getIntOrNull("id"),
                name = rs.getString("name"),
                genre = Genre(
                    id = rs.getIntOrNull("genre_id"),
                    name = rs.getString("genre_name")
                ),
                authors = emptyList()
            )
        }
        val SINGLE_ROW_MAPPER = RowMapper<Composition> { rs, _ ->
            val id = rs.getIntOrNull("id")
            val name = rs.getString("name")
            val genre = Genre(
                id = rs.getIntOrNull("genre_id"),
                name = rs.getString("genre_name")
            )
            val authors: MutableList<Author> = mutableListOf()
            do {
                try {
                    authors.add(
                        Author(
                            rs.getInt("author_id"),
                            rs.getString("author_name"),
                            rs.getString("author_nickname"),
                            rs.getDate("author_birth_date")
                        )
                    )
                } catch (e: NullPointerException) {
                    break
                }
            } while (rs.next())
            Composition(
                id,
                name,
                genre,
                authors = authors
            )
        }
    }
}