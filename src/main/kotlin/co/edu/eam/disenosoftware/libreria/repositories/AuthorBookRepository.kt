package co.edu.eam.disenosoftware.libreria.repositories

import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager
import co.edu.eam.disenosoftware.libreria.modelos.AuthorBook
import co.edu.eam.disenosoftware.libreria.modelos.Book
import co.edu.eam.disenosoftware.libreria.modelos.Author

class AuthorBookRepository {

    @Autowired
    lateinit var em: EntityManager

    fun create(authorBook: AuthorBook) {
        em.persist(authorBook)
    }

    fun find(id: Int): AuthorBook? {
        return em.find(AuthorBook::class.java, id)
    }

    fun update(authorBook: AuthorBook) {
        em.merge(authorBook)
    }

    fun delete(id: Int) {
        val authorBook = find(id)
        if (authorBook != null) {
            em.remove(authorBook)
        }
    }

    fun findByAuthor(id: Int): List<Book> {
        val query = em.createQuery("SELECT authorBook.book FROM AuthorBook authorBook WHERE authorBook.author.id = :code_author")
        query.setParameter("code_author", id)

        return query.resultList as List<Book>
    }

    fun findByBook(id: String): List<Author> {
        val query = em.createQuery("SELECT authorBook.author FROM AuthorBook authorBook WHERE authorBook.book.code = :code_book")
        query.setParameter("code_book", id)

        return query.resultList as List<Author>
    }
}