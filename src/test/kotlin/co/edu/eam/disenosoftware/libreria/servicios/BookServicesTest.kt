package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
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
class BookServicesTest {

    @Autowired
    lateinit var bookServices: BookServices

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreateBookByName() {
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        entityManager.persist(Book("1","EstrellasFeas","123",publisher))

        val exception = Assertions.assertThrows(
            BusinessException::class.java,
            {bookServices.createBook(Book("2","EstrellasFeas","123",publisher))})

        Assertions.assertEquals("This Name Book already exists", exception.message)
    }

    @Test
    fun testCreateBookByCode() {
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        entityManager.persist(Book("1","EstrellasFeas","123",publisher))

        try {
            bookServices.createBook(Book("1","EstrellasFeas","123",publisher))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This Book already exists", e.message)
        }
    }

}