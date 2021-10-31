package co.edu.eam.disenosoftware.libreria.services

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.Book
import co.edu.eam.disenosoftware.libreria.modelos.Borrow
import co.edu.eam.disenosoftware.libreria.modelos.Publisher
import co.edu.eam.disenosoftware.libreria.modelos.User
import co.edu.eam.disenosoftware.libreria.servicios.BorrowServices
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class BorrowServiceTest {

    @Autowired
    lateinit var borrowServices: BorrowServices

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createBorrowOnlyOneCopy(){
        val fecha = Date(2019,2,24)
        val publisher = Publisher(14,"Norma")
        entityManager.persist(publisher)
        val book = Book("123","Harry Poter","444",1,publisher)
        entityManager.persist(book)
        val user = User("111","Juan","Torres")
        entityManager.persist(user)
        val borrow=Borrow(45,fecha,book,user)

        try {
            borrowServices.createBorrow(borrow)
            Assertions.fail()
        }catch (e:BusinessException){
            Assertions.assertEquals("Only one copy of the book remains, therefore it cannot be loaned",e.message)
        }
    }

    @Test
    fun createBorrowUserHasFiveLoans(){
        val fecha = Date(2019,2,24)
        val publisher = Publisher(14,"Norma")
        entityManager.persist(publisher)
        val book = Book("123","Harry Poter","444",10,publisher)
        entityManager.persist(book)
        val user = User("111","Juan","Torres")
        entityManager.persist(user)
        val borrowOne = Borrow(45,fecha,book,user)
        entityManager.persist(borrowOne)
        val borrowTwo = Borrow(46,fecha,book,user)
        entityManager.persist(borrowTwo)
        val borrowThree = Borrow(47,fecha,book,user)
        entityManager.persist(borrowThree)
        val borrowFour = Borrow(48,fecha,book,user)
        entityManager.persist(borrowFour)
        val borrowFive = Borrow(49,fecha,book,user)
        entityManager.persist(borrowFive)
        val borrowSix = Borrow(50,fecha,book,user)

        try {
            borrowServices.createBorrow(borrowSix)
            Assertions.fail()
        }catch (e:BusinessException){
            Assertions.assertEquals("The user already has 5 loans, therefore this cannot be done",e.message)
        }
    }

    @Test
    fun createBorrowUserAlreadyHasLoanWithThisBook(){
        val fecha = Date(2019,2,24)
        val publisher = Publisher(14,"Norma")
        entityManager.persist(publisher)
        val book = Book("123","Harry Poter","444",10,publisher)
        entityManager.persist(book)
        val user = User("111","Juan","Torres")
        entityManager.persist(user)
        val borrowOne = Borrow(45,fecha,book,user)
        entityManager.persist(borrowOne)
        val borrowTwo  =Borrow(46,fecha,book,user)

        try {
            borrowServices.createBorrow(borrowTwo)
            Assertions.fail()
        }catch (e:BusinessException){
            Assertions.assertEquals("the user already made the loan of this book once, therefore the loan cannot be made",e.message)
        }
    }

    @Test
    fun createBorrowHappyPath(){
        val fecha = Date(2019,2,24)
        val publisher = Publisher(14,"Norma")
        entityManager.persist(publisher)
        val bookOne = Book("123","Harry Poter","444",10,publisher)
        entityManager.persist(bookOne)
        val bookTwo = Book("111","Juego de tronos","444",10,publisher)
        entityManager.persist(bookTwo)
        val user = User("111","Juan","Torres")
        entityManager.persist(user)
        val borrowOne = Borrow(45,fecha,bookOne,user)
        entityManager.persist(borrowOne)
        val borrowTwo = Borrow(46,fecha,bookTwo,user)

        borrowServices.createBorrow(borrowTwo)
        val borrow = entityManager.find(Borrow::class.java,borrowTwo.id)
        val book= entityManager.find(Book::class.java,bookTwo.code)
        Assertions.assertNotNull(borrow)
        Assertions.assertEquals(9,book.stock)
        Assertions.assertEquals("Juan",borrow.user.name)
        Assertions.assertEquals("Juego de tronos",borrow.book.name)
    }

    @Test
    fun findByUserNotExist(){
        try {
            borrowServices.findByUser("111")
            Assertions.fail()
        }catch (e:BusinessException){
            Assertions.assertEquals("This user does not exist in the system",e.message)
        }
    }

    @Test
    fun findByUserHappyPath(){
        val fecha = Date(2019,2,24)
        val publisher = Publisher(14,"Norma")
        entityManager.persist(publisher)
        val book = Book("123","Harry Poter","444",10,publisher)
        entityManager.persist(book)
        val user = User("111","Juan","Torres")
        entityManager.persist(user)
        val borrow = Borrow(45,fecha,book,user)
        entityManager.persist(borrow)

        val listBorrowByUser = borrowServices.findByUser("111")
        Assertions.assertEquals(1,listBorrowByUser.size)
    }

    @Test
    fun findUserByBookNotExist(){
        try {
            borrowServices.findUserByBook("111")
            Assertions.fail()
        }catch (e:BusinessException){
            Assertions.assertEquals("This book does not exist in the system",e.message)
        }
    }

    @Test
    fun findUserByBookHappyPath(){
        val fecha = Date(2019,2,24)
        val publisher = Publisher(14,"Norma")
        entityManager.persist(publisher)
        val book = Book("123","Harry Poter","444",10,publisher)
        entityManager.persist(book)
        val user = User("Juan","Torres","111")
        entityManager.persist(user)
        val borrow = Borrow(45,fecha,book,user)
        entityManager.persist(borrow)

        val listBorrowByUser= borrowServices.findUserByBook("123")
        Assertions.assertEquals(1,listBorrowByUser.size)
    }
}