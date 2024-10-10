package com.example.library.service

import com.example.library.dto.BookLogDto
import com.example.library.repository.BookLogRepository
import com.example.library.util.toDto
import com.example.library.util.toModel
import org.springframework.stereotype.Service

@Service
class BookLogServiceImpl(private val bookLogRepository: BookLogRepository) : BookLogService {
    override fun create(dto: BookLogDto): Int =
        bookLogRepository.create(dto.toModel())


    override fun update(id: Int, dto: BookLogDto) {
        bookLogRepository.update(id, dto.toModel())
    }

    override fun deleteById(id: Int) {
        bookLogRepository.deleteById(id)
    }

    override fun findById(id: Int): BookLogDto =
        bookLogRepository.findById(id)?.toDto() ?: throw RuntimeException("BookLog with id = $id not found")

    override fun getAll(): List<BookLogDto> =
        bookLogRepository.getAll().map { it.toDto() }

}