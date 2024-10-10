package com.example.library.controller

import com.example.library.dto.ReaderDto
import com.example.library.service.ReaderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reader")
class ReaderController(private val readerService: ReaderService) {
    @GetMapping
    fun getAll() : List<ReaderDto> = readerService.getAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int) : ReaderDto? = readerService.findById(id)

    @PostMapping
    fun create(@RequestBody readerDto: ReaderDto) {
        readerService.create(readerDto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody readerDto: ReaderDto) {
        readerService.update(id, readerDto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        readerService.deleteById(id)
    }
}