package com.example.library.controller

import com.example.library.dto.BookLogDto
import com.example.library.service.BookLogService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = arrayOf("http://localhost:3000"), maxAge = 3600)
@RestController
@RequestMapping("/bookLog")
class BookLogController(private val bookLogService: BookLogService) {
    @GetMapping
    fun getAll() : List<BookLogDto> = bookLogService.getAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int) : BookLogDto? = bookLogService.findById(id)

    @PostMapping
    fun create(@RequestBody bookLogDto: BookLogDto) {
        bookLogService.create(bookLogDto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody bookLogDto: BookLogDto) {
        bookLogService.update(id, bookLogDto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        bookLogService.deleteById(id)
    }
}