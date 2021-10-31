package co.edu.eam.disenosoftware.libreria.repositories

import co.edu.eam.disenosoftware.libreria.modelos.Book
import co.edu.eam.disenosoftware.libreria.modelos.Publisher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

class BookRepositoryTest {

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreate() {

        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        bookRepository.create(Book("1","EstrellasFeas","123",10,publisher))

        val book = entityManager.find(Book::class.java,  "1")
        Assertions.assertNotNull(book)
        Assertions.assertEquals("1", book.code)
        Assertions.assertEquals("EstrellasFeas", book.name)
        Assertions.assertEquals("123", book.isbn)
        Assertions.assertEquals("Santillana", book.publisher.name)
    }

    @Test
    fun testDelete(){
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        entityManager.persist(Book("1","EstrellasFeas","123",10,publisher))

        bookRepository.delete("3")

        val book = entityManager.find(Book::class.java, "1")
        Assertions.assertNull(book)
    }

    @Test
    fun findTest() {
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        entityManager.persist(Book("1","EstrellasFeas","123",10,publisher))

        val book = bookRepository.find("1")

        Assertions.assertNotNull(book)
        Assertions.assertEquals("EstrellasFeas", book?.name)
    }

    @Test
    fun testUpdate() {
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        entityManager.persist(Book("1","EstrellasFeas","123",10,publisher))
        entityManager.flush()

        val book = entityManager.find(Book::class.java, "1")
        book.name = "EstrellasBonitas"
        book.isbn = "456"
        entityManager.clear()

        bookRepository.update(book)

        val bookToAssert = entityManager.find(Book::class.java, "1")
        Assertions.assertEquals("EstrellasBonitas", bookToAssert.name)
        Assertions.assertEquals("456", bookToAssert.isbn)
    }

    @Test
    fun findByPublisherTest(){
        val publisher = Publisher(1,"Santillana")
        val publisher2 = Publisher(2,"JuanSe")
        entityManager.persist(publisher)
        entityManager.persist(publisher2)

        val book1 = Book("1","EstrellasFeas","123",10,publisher)
        val book2 = Book("2","EstrellasLindas","456",10,publisher2)
        val book3 = Book("3","EstrellasFeas","789",10,publisher)
        val book4 = Book("4","EstrellasLindas","963",10,publisher2)
        entityManager.persist(book1)
        entityManager.persist(book2)
        entityManager.persist(book3)
        entityManager.persist(book4)

        val libro = bookRepository.findByPublisher(1)
        val libro2 =bookRepository.findByPublisher(2)

        Assertions.assertEquals(2,libro.size)
        Assertions.assertEquals(2,libro2.size)
    }
}