package com.example.library.repository

import com.example.library.model.Genre
import com.example.library.util.getIntOrNull
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class GenreRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : GenreRepository {
    override fun create(libraryObject: Genre): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            "insert into genre (name) values (:name)",
            MapSqlParameterSource(
                mapOf(
                    "name" to libraryObject.name,
                )
            ),
            keyHolder,
            listOf("id").toTypedArray()
        )
        return keyHolder.keys?.getValue("id") as Int
    }

    override fun update(id: Int, libraryObject: Genre) {
        jdbcTemplate.update(
            "update genre set name = :name where id = :id",
            mapOf(
                "id" to id,
                "name" to libraryObject.name
            )
        )
    }

    override fun deleteById(id: Int) {
        jdbcTemplate.update(
            "delete from genre where id = :id",
            mapOf(
                "id" to id
            )
        )
    }

    override fun findById(id: Int): Genre? =
        jdbcTemplate.query(
            "select * from genre where id = :id",
            mapOf(
                "id" to id
            ),
            ROW_MAPPER
        ).firstOrNull()


    override fun getAll(): List<Genre> =
        jdbcTemplate.query(
            "select * from genre order by id",
            ROW_MAPPER
        )

    private companion object {
        val ROW_MAPPER = RowMapper<Genre> { rs, _ ->
            Genre(
                id = rs.getIntOrNull("id"),
                name = rs.getString("name"),
            )
        }
    }
}