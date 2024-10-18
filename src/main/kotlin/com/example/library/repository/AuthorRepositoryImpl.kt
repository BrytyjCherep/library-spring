package com.example.library.repository

import com.example.library.model.Author
import com.example.library.util.getIntOrNull
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class AuthorRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : AuthorRepository {
    override fun create(libraryObject: Author): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            "insert into author (name, nickname, birth_date) values (:name, :nickname, :birth_date)",
            MapSqlParameterSource(
                mapOf(
                    "name" to libraryObject.name,
                    "nickname" to libraryObject.nickname,
                    "birth_date" to libraryObject.birthDate
                )
            ),
            keyHolder,
            listOf("id").toTypedArray()
        )
        return keyHolder.keys?.getValue("id") as Int
    }

    override fun update(id: Int, libraryObject: Author) {
        jdbcTemplate.update(
            "update author set name = :name, nickname = :nickname, birth_date = :birth_date where id = :id",
            mapOf(
                "id" to id,
                "name" to libraryObject.name,
                "nickname" to libraryObject.nickname,
                "birth_date" to libraryObject.birthDate
            )
        )
    }

    override fun deleteById(id: Int) {
        jdbcTemplate.update(
            "delete from author where id = :id",
            mapOf(
                "id" to id
            )
        )
    }

    override fun findById(id: Int): Author? =
        jdbcTemplate.query(
            "select * from author where id = :id",
            mapOf(
                "id" to id
            ),
            ROW_MAPPER
        ).firstOrNull()


    override fun getAll(): List<Author> =
        jdbcTemplate.query(
            "select * from author order by id",
            ROW_MAPPER
        )

    private companion object {
        val ROW_MAPPER = RowMapper<Author> { rs, _ ->
            Author(
                id = rs.getIntOrNull("id"),
                name = rs.getString("name"),
                nickname = rs.getString("nickname"),
                birthDate = rs.getDate("birth_date")
            )
        }
    }
}