package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.entities.Book
import co.edu.eam.disenosoftware.libreria.modelos.entities.Borrow
import co.edu.eam.disenosoftware.libreria.modelos.entities.User
import co.edu.eam.disenosoftware.libreria.modelos.request.BorrowRequest
import co.edu.eam.disenosoftware.libreria.repositories.BookRepository
import co.edu.eam.disenosoftware.libreria.repositories.BorrowRepository
import co.edu.eam.disenosoftware.libreria.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityManager

@Service
class BorrowServices {
    @Autowired
    lateinit var borrowRepository: BorrowRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var bookRepository: BookRepository


    fun createBorrow(borrowRequest: BorrowRequest){

        var user = userRepository.find(borrowRequest.userId)

        if (user == null){
            throw BusinessException("This user does not exist")
        }

        var book = bookRepository.find(borrowRequest.bookId)

        if (book == null){
            throw BusinessException("This book does not exist")
        }

        if (book.stock == 1){
            throw BusinessException("Only one copy of the book remains, therefore it cannot be loaned")
        }

        var listBorrowsByUser = borrowRepository.findByUser(user.identification)

        if(listBorrowsByUser.size==5){
            throw BusinessException("The user already has 5 loans, therefore this cannot be done")
        }

        listBorrowsByUser.forEach { if(it.book.code== book.code){
            throw BusinessException("the user already made the loan of this book once, therefore the loan cannot be made")
        }

        }
        val date = Calendar.getInstance().time
        val borrow = Borrow(borrowRequest.id,date,book,user)

        book.stock = book.stock-1
        bookRepository.update(book)
        borrowRepository.create(borrow)
    }

    fun findByUser(idUser: String):List<Borrow>{

        var userById = userRepository.find(idUser)

        if (userById == null){
            throw BusinessException("This user does not exist in the system")
        }

        var listBorrowsByUser= borrowRepository.findByUser(idUser)
        return listBorrowsByUser

    }

    fun findUserByBook(codeBook: String): List<User>{
        var bookById = bookRepository.find(codeBook)

        if (bookById == null){
            throw BusinessException("This book does not exist in the system")
        }

        var listUsersByBook = borrowRepository.findUserByBook(codeBook)
        return listUsersByBook
    }

    fun returnBook(idBorrow: Int){

        val borrow = borrowRepository.find(idBorrow)

        if (borrow == null){
            throw BusinessException("This borrow does not exist")
        }
        val book = bookRepository.find(borrow?.book?.code)

        if (book == null){
            throw BusinessException("This book does not exist")
        }
        val user = userRepository.find(borrow?.user?.identification)

        if (user == null){
            throw BusinessException("This user does not exist")
        }

        book.stock= book.stock+1
        bookRepository.update(book)
        borrowRepository.delete(idBorrow)
    }
}