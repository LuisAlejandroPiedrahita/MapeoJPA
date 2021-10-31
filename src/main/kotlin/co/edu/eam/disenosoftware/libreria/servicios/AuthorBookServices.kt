package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.entities.AuthorBook
import co.edu.eam.disenosoftware.libreria.repositories.AuthorBookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class AuthorBookServices {

    @Autowired
    lateinit var authorBookRepository: AuthorBookRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createAuthorBook(authorBook: AuthorBook){
        val listaAutores = authorBookRepository.findByBook(authorBook.book.code)
        listaAutores.forEach { if (it.id == authorBook.author.id) {
            throw BusinessException("Este libro ya tiene un autor")
        } }
        authorBookRepository.create(authorBook)
    }

}