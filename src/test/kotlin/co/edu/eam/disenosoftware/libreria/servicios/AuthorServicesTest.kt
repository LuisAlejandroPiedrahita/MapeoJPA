package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.Author
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class AuthorServicesTest {

    @Autowired
    lateinit var authorServices: AuthorServices

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreateUserByCode() {
        entityManager.persist(Author(1, "luis","gomez"))

        try {
            authorServices.createAuthor(Author(1, "luis","gomez"))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This Author already exists", e.message)
        }
    }
}