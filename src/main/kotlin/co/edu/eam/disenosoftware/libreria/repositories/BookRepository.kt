package co.edu.eam.disenosoftware.libreria.repositories

import co.edu.eam.disenosoftware.libreria.modelos.entities.Book
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager

class BookRepository {
    @Autowired
    lateinit var em: EntityManager

    fun create(book: Book) {
        em.persist(book)
    }

    fun find(id: String?): Book? {
        return em.find(Book::class.java, id)
    }

    fun findNameBook(name: String): Book? {
        val query = em.createQuery("SELECT book FROM Book book WHERE book.name = :name")
        query.setParameter("name", name)

        val list = query.resultList as List<Book>

        return if (list.isEmpty()) null else list[0]
    }

    fun update(book: Book) {
        em.merge(book)
    }

    fun delete(id: String) {
        val book = find(id)
        if (book != null) {
            em.remove(book)
        }
    }

    fun findByPublisher(id: Int): List<Book> {
        val query = em.createQuery("SELECT book FROM Book book WHERE book.publisher.code = :code_publisher")
        query.setParameter("code_publisher", id)

        return query.resultList as List<Book>
    }
}