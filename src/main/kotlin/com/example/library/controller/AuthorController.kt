package com.example.library.controller

import com.example.library.dto.AuthorDto
import com.example.library.service.AuthorService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/author")
class AuthorController(private val authorService: AuthorService) {

    @GetMapping
    fun getAll() : List<AuthorDto> = authorService.getAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int) : AuthorDto? = authorService.findById(id)

    @PostMapping
    fun create(@RequestBody authorDto: AuthorDto) {
        authorService.create(authorDto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody authorDto: AuthorDto) {
        authorService.update(id, authorDto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        authorService.deleteById(id)
    }

}