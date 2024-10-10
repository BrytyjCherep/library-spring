package com.example.library.service

interface LibraryService<T> {
    fun create(dto: T) : Int

    fun update(id: Int, dto: T)

    fun deleteById(id: Int)

    fun findById(id: Int): T?

    fun getAll(): List<T>
}