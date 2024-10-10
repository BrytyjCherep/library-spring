package com.example.library.service

import com.example.library.dto.AuthorDto
import com.example.library.repository.AuthorRepository
import com.example.library.util.toDto
import com.example.library.util.toModel
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl(private val authorRepository: AuthorRepository) : AuthorService {
    override fun create(dto: AuthorDto): Int =
        authorRepository.create(dto.toModel())


    override fun update(id: Int, dto: AuthorDto) {
        authorRepository.update(id, dto.toModel())
    }

    override fun deleteById(id: Int) {
        authorRepository.deleteById(id)
    }

    override fun findById(id: Int): AuthorDto =
        authorRepository.findById(id)?.toDto() ?: throw RuntimeException("Author with id = $id not found")

    override fun getAll(): List<AuthorDto> =
        authorRepository.getAll().map { it.toDto() }


}