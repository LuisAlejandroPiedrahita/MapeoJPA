package co.edu.eam.disenosoftware.libreria.repositories

import co.edu.eam.disenosoftware.libreria.modelos.Publisher
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager

class PublisherRepository {
    @Autowired
    lateinit var em: EntityManager

    fun create(publisher: Publisher) {
        em.persist(publisher)
    }

    fun find(id: Int): Publisher? {
        return em.find(Publisher::class.java, id)
    }

    fun update(publisher: Publisher) {
        em.merge(publisher)
    }

    fun delete(id: Int) {
        val publisher = find(id)
        if (publisher != null) {
            em.remove(publisher)
        }
    }
}