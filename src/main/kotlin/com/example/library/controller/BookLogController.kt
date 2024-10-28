package com.example.library.controller

import com.example.library.dto.BookLogDto
import com.example.library.service.BookLogService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@CrossOrigin(origins = arrayOf("http://localhost:3000"), maxAge = 3600)
@RestController
@RequestMapping("/bookLog")
class BookLogController(private val bookLogService: BookLogService) {
    @GetMapping
    fun getAll(): List<BookLogDto> = bookLogService.getAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): BookLogDto? = bookLogService.findById(id)

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

    @PutMapping("/return/{id}")
    fun returnBook(@PathVariable id: Int) {
        bookLogService.returnBook(id, LocalDate.now())
    }

    @GetMapping("/report/{libraryBookId}/{readerId}")
    fun makeReport(@PathVariable  libraryBookId: Int, @PathVariable readerId: Int) : List<BookLogDto> =
        bookLogService.makeReport(libraryBookId, readerId)

}