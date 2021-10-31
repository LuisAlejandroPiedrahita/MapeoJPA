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

        val authorOne = Author(1, "luis","gomez")
        entityManager.persist(authorOne)
        val authorTwo= Author(1,"Beltran","Sandra")

        try {
            authorServices.createAuthor(authorTwo)
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This Author already exists", e.message)
        }
    }

    @Test
    fun createAuthorHappyPath(){
        val authorOne = Author(1, "luis","gomez")
        entityManager.persist(authorOne)
        val authorTwo= Author(1,"Beltran","Sandra")
        authorServices.createAuthor(authorTwo)

        val author= entityManager.find(Author::class.java,authorTwo.id)
        Assertions.assertNotNull(author)
        Assertions.assertEquals("Sandra",author.name)
        Assertions.assertEquals("Beltran",author.lastName)
    }
}