package com.example.library.controller

import com.example.library.dto.GenreDto
import com.example.library.service.GenreService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = arrayOf("http://localhost:3000"), maxAge = 3600)
@RestController
@RequestMapping("/genre")
class GenreController(private val  genreService: GenreService) {
    @GetMapping
    fun getAll() : List<GenreDto> = genreService.getAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int) : GenreDto? = genreService.findById(id)

    @PostMapping
    fun create(@RequestBody genreDto: GenreDto) {
        genreService.create(genreDto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody genreDto: GenreDto) {
        genreService.update(id, genreDto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        genreService.deleteById(id)
    }
}