package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.entities.User
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
        val userOne = User("111","pablo","perez")
        entityManager.persist(userOne)
        val userTwo = User("222","Luna","Martinez")
        try {
            userServices.createUser(userTwo)
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This User already exists", e.message)
        }
    }

    @Test
    fun createUserHappyPath(){
        val userOne = User("111","pablo","perez")
        entityManager.persist(userOne)
        val userTwo = User("222","Luna","Martinez")

        userServices.createUser(userTwo)
        val user = entityManager.find(User::class.java,userTwo.identification)
        Assertions.assertNotNull(user)
        Assertions.assertEquals("Luna",user.name)
        Assertions.assertEquals("Martinez",user.lastName)
    }
}