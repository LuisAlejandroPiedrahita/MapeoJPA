package co.edu.eam.disenosoftware.libreria.repositories

import co.edu.eam.disenosoftware.libreria.modelos.Book
import co.edu.eam.disenosoftware.libreria.modelos.Borrow
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager

class BorrowRepository {

    @Autowired
    lateinit var em: EntityManager

    fun create(borrow: Borrow) {
        em.persist(borrow)
    }

    fun find(id: Int): Borrow? {
        return em.find(Borrow::class.java, id)
    }

    fun update(borrow: Borrow) {
        em.merge(borrow)
    }

    fun delete(id: Int) {
        val borrow = find(id)
        if (borrow != null) {
            em.remove(borrow)
        }
    }

    fun findByUser(id: String): List<Borrow> {
        val query = em.createQuery("SELECT borrow FROM Borrow borrow WHERE borrow.user.identification = :code_user")
        query.setParameter("code_user", id)

        return query.resultList as List<Borrow>
    }

    fun findByBook(id: String): List<Borrow> {
        val query = em.createQuery("SELECT borrow FROM Borrow borrow WHERE borrow.book.code = :code_book")
        query.setParameter("code_book", id)

        return query.resultList as List<Borrow>
    }
}