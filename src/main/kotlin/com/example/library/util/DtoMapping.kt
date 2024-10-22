package com.example.library.util

import com.example.library.dto.*
import com.example.library.model.*

fun Author.toDto() = AuthorDto(
    id = id,
    name = name,
    nickname = nickname,
    birthDate = birthDate
)

fun AuthorDto.toModel() = Author(
    id = id,
    name = name,
    nickname = nickname,
    birthDate = birthDate
)

fun BookLog.toDto() = BookLogDto(
    id = id,
    libraryBook = libraryBook.toDto(),
    reader = reader.toDto(),
    issueDate = issueDate,
    returnDate = returnDate
    )

fun BookLogDto.toModel() = BookLog(
    id = id,
    libraryBook = libraryBook.toModel(),
    reader = reader.toModel(),
    issueDate = issueDate,
    returnDate = returnDate
)

fun Book.toDto() = BookDto (
    id = id,
    name = name,
    isbn = isbn,
    publicationDate = publicationDate,
    compositions = compositions?.map { it.toDto() }
)

fun BookDto.toModel() = Book (
    id = id,
    name = name,
    isbn = isbn,
    publicationDate = publicationDate,
    compositions = compositions?.map { it.toModel() }
)

fun Composition.toDto() = CompositionDto (
    id = id,
    name = name,
    genre = genre.toDto(),
    authors = authors?.map { it.toDto() }
)

fun CompositionDto.toModel() = Composition (
    id = id,
    name = name,
    genre = genre.toModel(),
    authors = authors?.map { it.toModel() }
)

fun Genre.toDto() = GenreDto(
    id = id,
    name = name
)

fun GenreDto.toModel() = Genre(
    id = id,
    name = name
)

fun LibraryBook.toDto() = LibraryBookDto(
    id = id,
    book = book.toDto()
)

fun LibraryBookDto.toModel() = LibraryBook(
    id = id,
    book = book.toModel()
)

fun Reader.toDto() = ReaderDto(
    id = id,
    name = name,
    email = email
)

fun ReaderDto.toModel() = Reader(
    id = id,
    name = name,
    email = email
)