package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.Book
import co.edu.eam.disenosoftware.libreria.repositories.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class BookServices {

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createBook(book: Book) {
        val booktById = bookRepository.find(book.code?:"")

        if(booktById != null){
            throw BusinessException("This Book already exists")
        }

        val bookByNamE = bookRepository.findNameBook(book.name)

        if(bookByNamE != null){
            throw BusinessException("This Name Book already exists")
        }

        bookRepository.create(book)
    }
}