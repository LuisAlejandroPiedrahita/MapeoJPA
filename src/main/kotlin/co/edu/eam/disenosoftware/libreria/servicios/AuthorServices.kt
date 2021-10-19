package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.Author
import co.edu.eam.disenosoftware.libreria.repositories.AuthorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class AuthorServices {

    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createAuthor(author: Author) {
        val authorById = authorRepository.find(author.id)

        if(authorById != null){
            throw BusinessException("This Author already exists")
        }
        authorRepository.create(author)
    }
}