package co.edu.eam.disenosoftware.libreria.repositories

import co.edu.eam.disenosoftware.libreria.modelos.entities.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

class UserRepositoryTest {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreatePerson() {

        userRepository.create(User("3", "pablo","perez"))

        val user = entityManager.find(User::class.java,  "3")
        Assertions.assertNotNull(user)
        Assertions.assertEquals("3", user.identification)
        Assertions.assertEquals("pablo", user.name)
        Assertions.assertEquals("perez", user.lastName)
    }

    @Test
    fun testDelete(){
        entityManager.persist(User("3", "pablo","perez"))

        userRepository.delete("3")

        val user = entityManager.find(User::class.java, "3")
        Assertions.assertNull(user)
    }

    @Test
    fun findTest() {
        entityManager.persist(User("3", "pablo", "perez"))

        val user = userRepository.find("3")

        Assertions.assertNotNull(user)
        Assertions.assertEquals("pablo", user?.name)
    }

    @Test
    fun testUpdate() {
        entityManager.persist(User("3", "pablo", "perez"))

        entityManager.flush()

        val user = entityManager.find(User::class.java, "3")
        user.name = "gladys"
        user.lastName = "lurdes"

        entityManager.clear()

        userRepository.update(user)

        val userToAssert = entityManager.find(User::class.java, "3")
        Assertions.assertEquals("gladys", userToAssert.name)
        Assertions.assertEquals("lurdes", userToAssert.lastName)
    }
}