package com.example.library.service

import com.example.library.dto.CompositionDto
import com.example.library.repository.CompositionRepository
import com.example.library.util.toDto
import com.example.library.util.toModel
import org.springframework.stereotype.Service

@Service
class CompositionServiceImpl(private val compositionRepository: CompositionRepository) : CompositionService {
    override fun create(dto: CompositionDto): Int =
        compositionRepository.create(dto.toModel())

    override fun update(id: Int, dto: CompositionDto) {
        compositionRepository.update(id, dto.toModel())
    }

    override fun deleteById(id: Int) {
        compositionRepository.deleteById(id)
    }

    override fun findById(id: Int): CompositionDto =
        compositionRepository.findById(id)?.toDto() ?: throw RuntimeException("Composition with id = $id not found")

    override fun getAll(): List<CompositionDto> =
        compositionRepository.getAll().map { it.toDto() }
}