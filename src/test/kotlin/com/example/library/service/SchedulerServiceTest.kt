package com.example.library.service

import com.example.library.model.*
import com.example.library.repository.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class SchedulerServiceTest {
    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Autowired
    lateinit var compositionRepository: CompositionRepository

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var genreRepository: GenreRepository

    @Autowired
    lateinit var libraryBookRepository: LibraryBookRepository

    @Autowired
    lateinit var readerRepository: ReaderRepository

    @Autowired
    lateinit var bookLogRepository: BookLogRepository

    @Autowired
    lateinit var schedulerService: SchedulerService

    var bookLog: BookLog? = null

    @BeforeEach
    fun setUp() {
        val auth1 = authorRepository.findById(
            authorRepository.create(
                Author(
                    null,
                    "Пушкин Александр Сергеевич",
                    null,
                    java.sql.Date(-101, 5, 26)
                )
            )
        )
        val auth2 = authorRepository.findById(
            authorRepository.create(
                Author(
                    null,
                    "Гоголь Николай Васильевич",
                    "gogol",
                    java.sql.Date(-91, 0, 20)
                )
            )
        )
        val genre = genreRepository.findById(genreRepository.create(Genre(null, "Ужасы")))
        val composition1 = compositionRepository.findById(
            compositionRepository.create(
                Composition(
                    null,
                    "Вий",
                    genre!!,
                    listOf(auth1!!, auth2!!)
                )
            )
        )
        val composition2 = compositionRepository.findById(
            compositionRepository.create(
                Composition(
                    null,
                    "Преступление и наказание",
                    genre!!,
                    listOf(auth1!!)
                )
            )
        )

        compositionRepository.create(composition1!!)
        compositionRepository.create(composition2!!)

        val book = bookRepository.findById(
            bookRepository.create(
                Book(
                    null,
                    "Сборник произведений Пушкина и Гоголя",
                    "12345678",
                    java.sql.Date(121, 2, 14),
                    listOf(composition1!!, composition2!!)
                )
            )
        )
        val libraryBook = libraryBookRepository.findById(
            libraryBookRepository.create(
                LibraryBook(
                    book = book!!
                )
            )
        )
        val reader = readerRepository.findById(
            readerRepository.create(
                Reader(
                    name = "Линёв Святослав",
                    email = "svyat-linev@yandex.ru"
                )
            )
        )
        bookLog = bookLogRepository.findById(
            bookLogRepository.create(
                BookLog(
                    libraryBook = libraryBook!!,
                    reader = reader!!,
                    issueDate = java.sql.Date(124, 8, 3)
                )
            )
        )
    }

    @Test
    fun getListOfOverdueReaders() {
        assert(mutableListOf(bookLog!!) == schedulerService.getListOfOverdueReaders())
    }
}