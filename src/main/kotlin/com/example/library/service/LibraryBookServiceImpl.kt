package com.example.library.service

import com.example.library.dto.LibraryBookDto
import com.example.library.repository.LibraryBookRepository
import com.example.library.util.toDto
import com.example.library.util.toModel
import org.springframework.stereotype.Service

@Service
class LibraryBookServiceImpl(private val libraryBookRepository: LibraryBookRepository) : LibraryBookService {
    override fun create(dto: LibraryBookDto): Int =
        libraryBookRepository.create(dto.toModel())

    override fun update(id: Int, dto: LibraryBookDto) {
        libraryBookRepository.update(id, dto.toModel())
    }

    override fun deleteById(id: Int) {
        libraryBookRepository.deleteById(id)
    }

    override fun findById(id: Int): LibraryBookDto =
        libraryBookRepository.findById(id)?.toDto() ?: throw RuntimeException("LibraryBook with id = $id not found")

    override fun getAll(): List<LibraryBookDto> =
        libraryBookRepository.getAll().map { it.toDto() }
}