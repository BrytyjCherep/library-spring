package com.example.library.controller

import com.example.library.dto.CompositionDto
import com.example.library.service.CompositionService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = arrayOf("http://localhost:3000"), maxAge = 3600)
@RestController
@RequestMapping("/composition")
class CompositionController(private val compositionService: CompositionService) {
    @GetMapping
    fun getAll() : List<CompositionDto> = compositionService.getAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int) : CompositionDto? = compositionService.findById(id)

    @PostMapping
    fun create(@RequestBody compositionDto: CompositionDto) {
        compositionService.create(compositionDto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody compositionDto: CompositionDto) {
        compositionService.update(id, compositionDto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        compositionService.deleteById(id)
    }
}