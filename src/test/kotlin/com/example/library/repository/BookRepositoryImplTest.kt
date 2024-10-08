package com.example.library.repository

import com.example.library.model.Author
import com.example.library.model.Book
import com.example.library.model.Composition
import com.example.library.model.Genre
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class BookRepositoryImplTest {

    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Autowired
    lateinit var compositionRepository: CompositionRepository

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var genreRepository: GenreRepository

    var auth1: Author? = null
    var auth2: Author? = null
    var genre: Genre? = null
    var composition1: Composition? = null
    var composition2: Composition? = null


    @BeforeEach
    fun setUp() {
        auth1 = authorRepository.findById(authorRepository.create(Author(null, "Пушкин", null, Date(800, 4, 10))))
        auth2 = authorRepository.findById(authorRepository.create(Author(null, "Гоголь", "gogol", Date(700, 3, 14))))
        genre = genreRepository.findById(genreRepository.create(Genre(null, "Ужасы")))
        composition1 = compositionRepository.findById(
            compositionRepository.create(
                Composition(
                    null,
                    "Вий",
                    genre!!,
                    listOf(auth1!!, auth2!!)
                )
            )
        )
        composition2 = compositionRepository.findById(
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
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun create() {
        val book = Book(null, "Create Book", "12345678", Date(700, 3, 14), listOf(composition1!!, composition2!!))
        val bookId = bookRepository.create(book)
        val testBook = bookRepository.findById(bookId)

        assertAll(
            "Create book",
            Executable { assertEquals("Create Book", testBook!!.name) },
            Executable { assertEquals("12345678", testBook!!.isbn) },
            Executable { assertEquals(Date(700, 3, 14), testBook!!.publicationDate) },
        )

    }

    @Test
    fun update() {
        val book = Book(null, "Book2", "1111111", Date(700, 3, 14), listOf(composition1!!, composition2!!))
        val bookId = bookRepository.create(book)
        val updateBook = Book(null, "Update Book", "87654321", Date(600, 6, 8), listOf(composition1!!, composition2!!))

        bookRepository.update(bookId, updateBook)
        val updatedBook = bookRepository.findById(bookId)

        assertAll(
            "Update book",
            Executable { assertEquals("Update Book", updatedBook!!.name) },
            Executable { assertEquals("87654321", updatedBook!!.isbn) },
            Executable { assertEquals(Date(600, 6, 8), updatedBook!!.publicationDate) },
        )
    }

    @Test
    fun deleteById() {
        val book = Book(null, "Delete Book", "1111111", Date(700, 3, 14), listOf(composition1!!, composition2!!))
        val bookId = bookRepository.create(book)
        bookRepository.deleteById(bookId)
        val deletedBook = bookRepository.findById(bookId)
        assertNull(deletedBook)
    }

    @Test
    fun findById() {
        val book = Book(null, "FindById Book", "1111111", Date(700, 3, 14), listOf(composition1!!, composition2!!))
        val bookId = bookRepository.create(book)
        val findedBook = bookRepository.findById(bookId)
        assertAll(
            "FindById book",
            Executable { assertEquals(book.name, findedBook!!.name) },
            Executable { assertEquals(book.isbn, findedBook!!.isbn) },
            Executable { assertEquals(book.publicationDate, findedBook!!.publicationDate) },
        )
    }

    @Test
    fun getAll() {
        bookRepository.deleteById(1)
        val book = Book(null, "GetAll Book", "2221111", Date(465, 1, 17), listOf(composition1!!, composition2!!))
        val book2 = Book(null, "GetAll Book2", "4321234", Date(597, 10, 20), listOf(composition1!!, composition2!!))
        val bookList = mutableListOf(book, book2)
        bookRepository.create(book)
        bookRepository.create(book2)
        val bookGetList = bookRepository.getAll()

        for (i in bookGetList.indices) {
            assertAll(
                Executable { assertEquals(bookList[i].name, bookGetList[i].name) },
                Executable { assertEquals(bookList[i].isbn, bookGetList[i].isbn) },
                Executable { assertEquals(bookList[i].publicationDate, bookGetList[i].publicationDate) },
            )
        }
    }

    @Test
    fun update2() {
        val book = Book(null, "Book2", "1111111", Date(700, 3, 14), listOf(composition1!!))
        val bookId = bookRepository.create(book)
        val updateBook = Book(null, "Update Book", "87654321", Date(600, 6, 8), listOf(composition1!!, composition2!!))

        bookRepository.update(bookId, updateBook)
        val updatedBook = bookRepository.findById(bookId)

        assert(updatedBook?.compositions?.size == 2)
    }

    @Test
    fun update3() {
        val book = Book(null, "Book2", "1111111", Date(700, 3, 14), listOf())
        val bookId = bookRepository.create(book)
        val updateBook = Book(null, "Update Book", "87654321", Date(600, 6, 8), listOf(composition1!!, composition2!!))

        bookRepository.update(bookId, updateBook)
        val updatedBook = bookRepository.findById(bookId)

        assert(updatedBook?.compositions?.size == 2)
    }

    @Test
    fun update4() {
        val book = Book(null, "Book2", "1111111", Date(700, 3, 14), listOf(composition1!!, composition2!!))
        val bookId = bookRepository.create(book)
        val updateBook = Book(null, "Update Book", "87654321", Date(600, 6, 8), listOf(composition2!!))

        bookRepository.update(bookId, updateBook)
        val updatedBook = bookRepository.findById(bookId)

        assert(updatedBook?.compositions?.size == 1)
    }

    @Test
    fun update5() {
        val book = Book(null, "Book2", "1111111", Date(700, 3, 14), listOf(composition1!!, composition2!!))
        val bookId = bookRepository.create(book)
        val updateBook = Book(null, "Update Book", "87654321", Date(600, 6, 8), listOf())

        bookRepository.update(bookId, updateBook)
        val updatedBook = bookRepository.findById(bookId)

        assert(updatedBook?.compositions?.size == 0)
    }
}