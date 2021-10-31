package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.entities.Book
import co.edu.eam.disenosoftware.libreria.modelos.entities.Publisher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class BookServicesTest {

    @Autowired
    lateinit var bookServices: BookServices

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreateBookByName() {
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        val bookOne = Book("123","Harry Poter","444",12,publisher)
        entityManager.persist(bookOne)
        val bookTwo = Book("123","Juego de tronos","666",11,publisher)

        try {
            bookServices.createBook(bookTwo,11)
            Assertions.fail()
        }catch (e: BusinessException){
            Assertions.assertEquals("This book with this name already exist",e.message)
        }
    }

    @Test
    fun testCreateBookByCode() {
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        val bookOne = Book("123","Harry Poter","444",12,publisher)
        entityManager.persist(bookOne)
        val bookTwo = Book("123","Juego de tronos","666",11,publisher)

        try {
            bookServices.createBook(bookTwo,11)
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This Book already exists", e.message)
        }
    }

    @Test
    fun createBookHappyPath(){
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        val bookOne = Book("123","Harry Poter","444",12,publisher)
        entityManager.persist(bookOne)
        val bookTwo = Book("123","Juego de tronos","666",11,publisher)

        bookServices.createBook(bookTwo,11)
        val book= entityManager.find(Book::class.java,bookTwo.code)
        Assertions.assertNotNull(book)
        Assertions.assertEquals("Juego de tronos",book.name)
        Assertions.assertEquals("666",book.isbn)
        Assertions.assertEquals("Santillana",book.publisher.name)
    }

    @Test
    fun returnBookThisBookNotExist(){
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        val bookOne = Book("123","Harry Poter","444",12,publisher)
        try {
            bookServices.returnBook(bookOne)
            Assertions.fail()
        }catch (e:BusinessException){
            Assertions.assertEquals("this book is not in the database",e.message)
        }
    }

    @Test
    fun returnBookHappyPath(){
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        val bookOne = Book("123","Harry Poter","444",12,publisher)
        entityManager.persist(bookOne)

        bookServices.returnBook(bookOne)
        val book= entityManager.find(Book::class.java,bookOne.code)
        Assertions.assertEquals(13,book.stock)
    }

}