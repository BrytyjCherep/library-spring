package com.example.library.controller

import com.example.library.dto.BookDto
import com.example.library.service.BookService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = arrayOf("http://localhost:3000"), maxAge = 3600)
@RestController
@RequestMapping("/book")
class BookController(private val bookService: BookService) {
    @GetMapping
    fun getAll() : List<BookDto> = bookService.getAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int) : BookDto? = bookService.findById(id)

    @PostMapping
    fun create(@RequestBody bookDto: BookDto) {
        bookService.create(bookDto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody bookDto: BookDto) {
        bookService.update(id, bookDto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        bookService.deleteById(id)
    }
}