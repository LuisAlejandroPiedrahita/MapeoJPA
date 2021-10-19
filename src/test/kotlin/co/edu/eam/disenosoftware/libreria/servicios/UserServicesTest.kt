package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class UserServicesTest {

    @Autowired
    lateinit var userServices: UserServices

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreateUserCode() {
        entityManager.persist(User("3", "pablo", "perez"))
        try {
            userServices.createUser(User("3", "pablo", "perez"))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This User already exists", e.message)
        }
    }

}