package co.edu.eam.disenosoftware.libreria.repositories

import co.edu.eam.disenosoftware.libreria.modelos.entities.Publisher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

class PublisherRepositoryTest {

    @Autowired
    lateinit var publisherRepository: PublisherRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreatePerson() {

        publisherRepository.create(Publisher(1,"Santillana"))

        val publisher = entityManager.find(Publisher::class.java,  1)
        Assertions.assertNotNull(publisher)
        Assertions.assertEquals(1, publisher.code)
        Assertions.assertEquals("Santillana", publisher.name)
    }

    @Test
    fun testDelete(){
        entityManager.persist(Publisher(1, "Santillana"))

        publisherRepository.delete(1)

        val publisher = entityManager.find(Publisher::class.java, 1)
        Assertions.assertNull(publisher)
    }

    @Test
    fun findTest() {
        entityManager.persist(Publisher(1, "Santillana"))

        val publisher = publisherRepository.find(1)

        Assertions.assertNotNull(publisher)
        Assertions.assertEquals("Santillana", publisher?.name)
    }

    @Test
    fun testUpdate() {
        entityManager.persist(Publisher(1, "Santillana"))
        entityManager.flush()

        val publisher = entityManager.find(Publisher::class.java, 1)
        publisher.name = "JuanSe"
        entityManager.clear()

        publisherRepository.update(publisher)

        val publisherToAssert = entityManager.find(Publisher::class.java, 1)
        Assertions.assertEquals("JuanSe", publisherToAssert.name)
    }
}