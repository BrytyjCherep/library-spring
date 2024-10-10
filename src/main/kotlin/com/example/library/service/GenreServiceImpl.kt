package com.example.library.service

import com.example.library.dto.GenreDto
import com.example.library.repository.GenreRepository
import com.example.library.util.toDto
import com.example.library.util.toModel
import org.springframework.stereotype.Service

@Service
class GenreServiceImpl(private val genreRepository: GenreRepository) : GenreService {
    override fun create(dto: GenreDto): Int =
        genreRepository.create(dto.toModel())

    override fun update(id: Int, dto: GenreDto) {
        genreRepository.update(id, dto.toModel())
    }

    override fun deleteById(id: Int) {
        genreRepository.deleteById(id)
    }

    override fun findById(id: Int): GenreDto =
        genreRepository.findById(id)?.toDto() ?: throw RuntimeException("Genre with id = $id not found")

    override fun getAll(): List<GenreDto> =
        genreRepository.getAll().map { it.toDto() }
}