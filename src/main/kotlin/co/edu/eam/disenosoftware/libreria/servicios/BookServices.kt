package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.entities.Book
import co.edu.eam.disenosoftware.libreria.repositories.BookRepository
import co.edu.eam.disenosoftware.libreria.repositories.PublisherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class BookServices {

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var publisherRepository: PublisherRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createBook(book: Book, idPublicher: Int) {
        val publisher = publisherRepository.find(idPublicher)

        if(publisher == null){
            throw BusinessException("this publisher does not exist")
        }

        val booktById = bookRepository.find(book.code?:"")

        if(booktById != null){
            throw BusinessException("This Book already exists")
        }

        val bookByNamE = bookRepository.findNameBook(book.name)

        if(bookByNamE != null){
            throw BusinessException("This Name Book already exists")
        }
        book.publisher = publisher
        bookRepository.create(book)
    }

    fun returnBook(book: Book){
        var bookById= bookRepository.find(book.code)

        if ( bookById == null){
            throw BusinessException("this book is not in the database")
        }

        bookById.stock= bookById.stock+1
        bookRepository.update(book)
    }

    fun editBook(book: Book){
        bookRepository.find(book.code)?:throw BusinessException("This book does not exist")
        bookRepository.update(book)
    }
}