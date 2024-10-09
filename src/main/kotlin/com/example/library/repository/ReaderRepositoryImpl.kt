package com.example.library.repository

import com.example.library.model.Reader
import com.example.library.util.getIntOrNull
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class ReaderRepositoryImpl (
    private val jdbcTemplate: NamedParameterJdbcTemplate
): ReaderRepository {
    override fun create(libraryObject: Reader): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            "insert into reader (name, email) values (:name, :email)",
            MapSqlParameterSource(
                mapOf(
                    "name" to libraryObject.name,
                    "email" to libraryObject.email
                )
            ),
            keyHolder,
            listOf("id").toTypedArray()
        )
        return keyHolder.keys?.getValue("id") as Int
    }

    override fun update(id: Int, libraryObject: Reader) {
        jdbcTemplate.update(
            "update reader set name = :name, email = :email where id = :id",
            mapOf(
                "id" to id,
                "name" to libraryObject.name,
                "email" to libraryObject.email,
            )
        )
    }

    override fun deleteById(id: Int) {
        jdbcTemplate.update(
            "delete from reader where id = :id",
            mapOf(
                "id" to id
            )
        )
    }

    override fun findById(id: Int): Reader? =
        jdbcTemplate.query(
            "select * from reader where id = :id",
            mapOf(
                "id" to id
            ) ,
            ROW_MAPPER
        ).firstOrNull()

    override fun getAll(): List<Reader> =
        jdbcTemplate.query(
            "select * from reader",
            ROW_MAPPER
        )

    private companion object {
        val ROW_MAPPER = RowMapper<Reader> { rs, _ ->
            Reader(
                id = rs.getIntOrNull("id"),
                name = rs.getString("name"),
                email = rs.getString("email")

            )
        }
    }
}