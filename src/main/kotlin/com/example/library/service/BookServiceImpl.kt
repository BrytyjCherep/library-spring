package com.example.library.service

import com.example.library.dto.BookDto
import com.example.library.repository.BookRepository
import com.example.library.util.toDto
import com.example.library.util.toModel
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(private val bookRepository: BookRepository) : BookService {
    override fun create(dto: BookDto): Int =
        bookRepository.create(dto.toModel())


    override fun update(id: Int, dto: BookDto) {
        bookRepository.update(id, dto.toModel())
    }

    override fun deleteById(id: Int) {
        bookRepository.deleteById(id)
    }

    override fun findById(id: Int): BookDto? =
        bookRepository.findById(id)?.toDto() ?: throw RuntimeException("Book with id = $id not found")

    override fun getAll(): List<BookDto> =
        bookRepository.getAll().map { it.toDto() }
}