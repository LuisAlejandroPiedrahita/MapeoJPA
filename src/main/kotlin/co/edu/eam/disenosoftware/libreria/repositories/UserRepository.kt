package co.edu.eam.disenosoftware.libreria.repositories

import co.edu.eam.disenosoftware.libreria.modelos.User
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager

class UserRepository {
    @Autowired
    lateinit var em: EntityManager

    fun create(user: User) {
        em.persist(user)
    }

    fun find(id: String): User? {
        return em.find(User::class.java, id)
    }

    fun update(user: User) {
        em.merge(user)
    }

    fun delete(id: String) {
        val user = find(id)
        if (user != null) {
            em.remove(user)
        }
    }
}