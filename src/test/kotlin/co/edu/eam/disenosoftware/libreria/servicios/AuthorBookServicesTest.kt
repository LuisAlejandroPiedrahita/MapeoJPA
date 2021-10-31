package co.edu.eam.disenosoftware.libreria.services

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.Author
import co.edu.eam.disenosoftware.libreria.modelos.AuthorBook
import co.edu.eam.disenosoftware.libreria.modelos.Book
import co.edu.eam.disenosoftware.libreria.modelos.Publisher
import co.edu.eam.disenosoftware.libreria.servicios.AuthorBookServices
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class AuthorBookServiceTest {

    @Autowired
    lateinit var authorBookServices:AuthorBookServices

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createAuthorBookAuthorRepeated(){
        val publisher = Publisher(12,"norma")
        entityManager.persist(publisher)
        val bookOne = Book("123","Harry Poter","444",5,publisher)
        entityManager.persist(bookOne)
        val bookTwo = Book("421","Alicia en el pais de las maravillas","555",6,publisher)
        entityManager.persist(bookTwo)
        val bookThree = Book("521","Los juegos del hambre","666",8,publisher)
        entityManager.persist(bookThree)
        val authorOne = Author(12,"Torres","Juan")
        entityManager.persist(authorOne)
        val authorTwo = Author(13,"Linares","Jose")
        entityManager.persist(authorTwo)
        val authorBookOne = AuthorBook(1,bookOne,authorTwo)
        entityManager.persist(authorBookOne)
        val authorBooktwo = AuthorBook(2,bookOne,authorTwo)
        try {
            authorBookServices.createAuthorBook(authorBooktwo)
            Assertions.fail()
        }catch (e: BusinessException){
            Assertions.assertEquals("This book already has this author", e.message)
        }

    }

    @Test
    fun cretaAuthorBookHappyPath(){
        val publisher = Publisher(12,"norma")
        entityManager.persist(publisher)
        val bookOne = Book("123","Harry Poter","444",5,publisher)
        entityManager.persist(bookOne)
        val bookTwo = Book("421","Alicia en el pais de las maravillas","555",6,publisher)
        entityManager.persist(bookTwo)
        val bookThree = Book("521","Los juegos del hambre","666",8,publisher)
        entityManager.persist(bookThree)
        val authorOne = Author(12,"Torres","Juan")
        entityManager.persist(authorOne)
        val authorTwo = Author(13,"Linares","Jose")
        entityManager.persist(authorTwo)
        val authorBookOne = AuthorBook(1,bookOne,authorTwo)
        entityManager.persist(authorBookOne)
        val authorBooktwo = AuthorBook(2,bookOne,authorOne)

        authorBookServices.createAuthorBook(authorBooktwo)
        val authorBook = entityManager.find(AuthorBook::class.java,authorBooktwo.id)
        Assertions.assertNotNull(authorBook)

        Assertions.assertEquals("Harry Poter",authorBook.book.name)
        Assertions.assertEquals("Juan",authorBook.author.name)

    }
}