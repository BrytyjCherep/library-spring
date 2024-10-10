package com.example.library.controller

import com.example.library.dto.LibraryBookDto
import com.example.library.service.LibraryBookService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/libraryBook")
class LibraryBookController(private  val libraryBookService: LibraryBookService) {
    @GetMapping
    fun getAll() : List<LibraryBookDto> = libraryBookService.getAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int) : LibraryBookDto? = libraryBookService.findById(id)

    @PostMapping
    fun create(@RequestBody libraryBookDto: LibraryBookDto) {
        libraryBookService.create(libraryBookDto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody libraryBookDto: LibraryBookDto) {
        libraryBookService.update(id, libraryBookDto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        libraryBookService.deleteById(id)
    }
}