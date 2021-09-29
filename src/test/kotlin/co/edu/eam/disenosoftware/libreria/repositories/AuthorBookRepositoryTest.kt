package co.edu.eam.disenosoftware.libreria.repositories

import co.edu.eam.disenosoftware.libreria.modelos.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

class AuthorBookRepositoryTest {
    @Autowired
    lateinit var authorBookRepository: AuthorBookRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreatePerson() {
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        val book = Book("1","EstrellasFeas","123",publisher)
        entityManager.persist(book)

        val author = Author(1, "luis","gomez")
        entityManager.persist(author)

        authorBookRepository.create(AuthorBook(1, book,author))

        val authorBook = entityManager.find(AuthorBook::class.java, 1)
        Assertions.assertNotNull(authorBook)
        Assertions.assertEquals(1, authorBook.id)
        Assertions.assertEquals("Santillana", authorBook.book.publisher.name)
        Assertions.assertEquals("EstrellasFeas", authorBook.book.name)
        Assertions.assertEquals("luis", authorBook.author.name)
    }

    @Test
    fun testDelete(){
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        val book = Book("1","EstrellasFeas","123",publisher)
        entityManager.persist(book)

        val author = Author(1, "luis","gomez")
        entityManager.persist(author)

        entityManager.persist(AuthorBook(1, book,author))

        authorBookRepository.delete(1)

        val authorBook = entityManager.find(AuthorBook::class.java, 1)
        Assertions.assertNull(authorBook)
    }

    @Test
    fun findTest() {
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        val book = Book("1","EstrellasFeas","123",publisher)
        entityManager.persist(book)

        val author = Author(1, "luis","gomez")
        entityManager.persist(author)

        entityManager.persist(AuthorBook(1, book,author))

        val authorBook = authorBookRepository.find(1)

        Assertions.assertNotNull(authorBook)
        Assertions.assertEquals("luis", authorBook?.author?.name)
    }

    @Test
    fun testUpdate() {
        val publisher = Publisher(1, "Santillana")
        entityManager.persist(publisher)

        val book = Book("1", "EstrellasFeas", "123", publisher)
        entityManager.persist(book)

        val author = Author(1, "luis", "gomez")
        entityManager.persist(author)

        entityManager.persist(AuthorBook(1, book, author))
        entityManager.flush()

        val authorBook = entityManager.find(AuthorBook::class.java, 1)
        authorBook.book.name = "llamasSensibles"
        authorBook.author.name = "gertrudis"
        entityManager.clear()

        authorBookRepository.update(authorBook)

        val authorBookToAssert = entityManager.find(AuthorBook::class.java, 1)
        Assertions.assertEquals("llamasSensibles", authorBookToAssert.book.name)
        Assertions.assertEquals("gertrudis", authorBookToAssert.author.name)
    }

    @Test
    fun findByAuthorTest(){
        val publisher = Publisher(1,"Santillana")
        val publisher2 = Publisher(2,"JuanSe")
        entityManager.persist(publisher)
        entityManager.persist(publisher2)

        val book1 = Book("1","EstrellasFeas","123",publisher)
        val book2 = Book("2","EstrellasLindas","456",publisher2)
        entityManager.persist(book1)
        entityManager.persist(book2)

        val author1 = Author(1, "luis","gomez")
        val author2 = Author(2, "juan","lucho")
        entityManager.persist(author1)
        entityManager.persist(author2)

        val libroAutor1 = AuthorBook(1,book1,author1)
        val libroAutor2 = AuthorBook(2,book2,author2)

        val authorBook1 = authorBookRepository.findByAuthor(1)
        val authorBook2 = authorBookRepository.findByAuthor(2)

        Assertions.assertEquals(1,authorBook1.size)
        Assertions.assertEquals(1,authorBook2.size)
    }

    @Test
    fun findByBookTest(){
        val publisher = Publisher(1,"Santillana")
        val publisher2 = Publisher(2,"JuanSe")
        entityManager.persist(publisher)
        entityManager.persist(publisher2)

        val book1 = Book("1","EstrellasFeas","123",publisher)
        val book2 = Book("2","EstrellasLindas","456",publisher2)
        entityManager.persist(book1)
        entityManager.persist(book2)

        val author1 = Author(1, "luis","gomez")
        val author2 = Author(2, "juan","lucho")
        entityManager.persist(author1)
        entityManager.persist(author2)

        val libroAutor1 = AuthorBook(1,book1,author1)
        val libroAutor2 = AuthorBook(2,book2,author2)

        val authorBook1 = authorBookRepository.findByBook("1")
        val authorBook2 = authorBookRepository.findByBook("2")

        Assertions.assertEquals(1,authorBook1.size)
        Assertions.assertEquals(1,authorBook2.size)
    }
}