package com.example.library.repository

interface LibraryRepository<T> {
    fun create(libraryObject: T) : Int

    fun update(id: Int, libraryObject: T)

    fun deleteById(id: Int)

    fun findById(id: Int): T?

    fun getAll(): List<T>

}