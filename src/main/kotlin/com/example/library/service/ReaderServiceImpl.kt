package com.example.library.service

import com.example.library.dto.ReaderDto
import com.example.library.repository.ReaderRepository
import com.example.library.util.toDto
import com.example.library.util.toModel
import org.springframework.stereotype.Service

@Service
class ReaderServiceImpl(private val readerRepository: ReaderRepository) : ReaderService {
    override fun create(dto: ReaderDto): Int =
        readerRepository.create(dto.toModel())

    override fun update(id: Int, dto: ReaderDto) {
        readerRepository.update(id, dto.toModel())
    }

    override fun deleteById(id: Int) {
        readerRepository.deleteById(id)
    }

    override fun findById(id: Int): ReaderDto? =
        readerRepository.findById(id)?.toDto() ?: throw RuntimeException("Reader with id = $id not found")

    override fun getAll(): List<ReaderDto> =
        readerRepository.getAll().map { it.toDto() }
}