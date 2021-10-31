package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.Publisher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class PublisherServicesTest {

    @Autowired
    lateinit var publisherServices: PublisherServices

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreatePublisher() {
        val publisherOne = Publisher(1, "Santillana")
        entityManager.persist(publisherOne)
        val publisherTwo = Publisher(1, "Norma")
        try {
            publisherServices.createPublisher(publisherTwo)
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This Publisher already exists", e.message)
        }
    }

    @Test
    fun createPublisherHappyPath(){
        val publisherOne = Publisher(1, "Santillana")
        entityManager.persist(publisherOne)
        val publisherTwo = Publisher(1, "Norma")

        publisherServices.createPublisher(publisherTwo)
        val publisher= entityManager.find(Publisher::class.java,publisherTwo.code)
        Assertions.assertNotNull(publisher)

        Assertions.assertEquals("Norma",publisher.name)
    }
}