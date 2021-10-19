package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.Book
import co.edu.eam.disenosoftware.libreria.modelos.Borrow
import co.edu.eam.disenosoftware.libreria.modelos.User
import co.edu.eam.disenosoftware.libreria.repositories.BorrowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class BorrowServices {
    @Autowired
    lateinit var borrowRepository: BorrowRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun validateBorrowUser(borrow: Borrow){
        val usuario = entityManager.find(User:: class.java,borrow.user.identification)
        if (usuario == null){
            throw BusinessException("El usuario no existe")
        }
        borrowRepository.create(borrow)
    }

    fun validateBorrowBook(borrow: Borrow){
        val libro = entityManager.find(Book:: class.java,borrow.book.code)
        if (libro == null){
            throw BusinessException("El libro no existe")
        }
        borrowRepository.create(borrow)
    }
}