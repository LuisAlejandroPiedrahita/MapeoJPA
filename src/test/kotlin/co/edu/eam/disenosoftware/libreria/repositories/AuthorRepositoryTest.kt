package co.edu.eam.disenosoftware.libreria.repositories

import co.edu.eam.disenosoftware.libreria.modelos.entities.Author
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

class AuthorRepositoryTest {
    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreatePerson() {

        authorRepository.create(Author(1, "luis","gomez"))

        val author = entityManager.find(Author::class.java,  1)
        Assertions.assertNotNull(author)
        Assertions.assertEquals(1, author.id)
        Assertions.assertEquals("luis", author.name)
        Assertions.assertEquals("gomez", author.lastName)
    }

    @Test
    fun testDelete(){
        entityManager.persist(Author(1, "luis","gomez"))

        authorRepository.delete(1)

        val author = entityManager.find(Author::class.java, 1)
        Assertions.assertNull(author)
    }

    @Test
    fun findTest() {
        entityManager.persist(Author(1, "luis","gomez"))

        val author = authorRepository.find(1)

        Assertions.assertNotNull(author)
        Assertions.assertEquals("luis", author?.name)
    }

    @Test
    fun testUpdate() {
        entityManager.persist(Author(1, "luis","gomez"))
        entityManager.flush()

        val author = entityManager.find(Author::class.java, 1)
        author.name = "alejandro"
        author.lastName = "garcia"
        entityManager.clear()

        authorRepository.update(author)

        val authorToAssert = entityManager.find(Author::class.java, 1)
        Assertions.assertEquals("alejandro", authorToAssert.name)
        Assertions.assertEquals("garcia", authorToAssert.lastName)
    }
}