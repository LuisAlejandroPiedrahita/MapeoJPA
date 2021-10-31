package co.edu.eam.disenosoftware.libreria.repositories

import co.edu.eam.disenosoftware.libreria.modelos.User
import co.edu.eam.disenosoftware.libreria.modelos.Borrow
import co.edu.eam.disenosoftware.libreria.modelos.Book
import co.edu.eam.disenosoftware.libreria.modelos.Publisher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

class BorrowRepositoryTest {
    @Autowired
    lateinit var borrowRepository: BorrowRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreate() {
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        val book = Book("1","EstrellasFeas","123",10,publisher)
        entityManager.persist(book)

        val user = User("1", "luis","gomez")
        entityManager.persist(user)

        val date = Date(2021,9,28)

        borrowRepository.create(Borrow(1,date,book,user))

        val borrow = entityManager.find(Borrow::class.java, 1)
        Assertions.assertNotNull(borrow)
        Assertions.assertEquals(1, borrow.id)
        Assertions.assertEquals(date, borrow.dateTime)
        Assertions.assertEquals("EstrellasFeas", borrow.book.name)
        Assertions.assertEquals("luis", borrow.user.name)
    }

    @Test
    fun testDelete(){
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        val book = Book("1","EstrellasFeas","123",10,publisher)
        entityManager.persist(book)

        val user = User("1", "luis","gomez")
        entityManager.persist(user)

        val date = Date(2021,9,28)

        borrowRepository.create(Borrow(1,date,book,user))

        borrowRepository.delete(1)

        val borrow = entityManager.find(Borrow::class.java, 1)
        Assertions.assertNull(borrow)
    }

    @Test
    fun findTest() {
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        val book = Book("1","EstrellasFeas","123",10,publisher)
        entityManager.persist(book)

        val user = User("1", "luis","gomez")
        entityManager.persist(user)

        val date = Date(2021,9,28)

        borrowRepository.create(Borrow(1,date,book,user))

        val borrow = borrowRepository.find(1)

        Assertions.assertNotNull(borrow)
        Assertions.assertEquals("luis", borrow?.user?.name)
    }

    @Test
    fun testUpdate() {
        val publisher = Publisher(1,"Santillana")
        entityManager.persist(publisher)

        val book = Book("1","EstrellasFeas","123",10,publisher)
        entityManager.persist(book)

        val user = User("1", "luis","gomez")
        entityManager.persist(user)

        val date = Date(2021,9,28)

        entityManager.persist(Borrow(1,date,book,user))

        entityManager.flush()

        val borrow = entityManager.find(Borrow::class.java, 1)
        borrow.dateTime = Date(2021,9,29)
        entityManager.clear()

        borrowRepository.update(borrow)

        val borrowToAssert = entityManager.find(Borrow::class.java, 1)
        Assertions.assertEquals("EstrellasFeas", borrowToAssert.book.name)
        Assertions.assertEquals("luis", borrowToAssert.user.name)
    }

    @Test
    fun findByUserTest(){
        val publisher = Publisher(1,"Santillana")
        val publisher2 = Publisher(2,"JuanSe")
        entityManager.persist(publisher)
        entityManager.persist(publisher2)

        val book1 = Book("1","EstrellasFeas","123",10,publisher)
        val book2 = Book("2","EstrellasLindas","456",10,publisher2)
        entityManager.persist(book1)
        entityManager.persist(book2)

        val user1 = User("1", "luis","gomez")
        val user2 = User("2", "juan","lucho")
        entityManager.persist(user1)
        entityManager.persist(user2)

        val date = Date(2021,9,28)

        val borrow1 = Borrow(1,date,book1,user1)
        val borrow2 = Borrow(2,date,book2,user2)

        val prestamo1 = borrowRepository.findByUser("1")
        val prestamo2 = borrowRepository.findByUser("2")

        Assertions.assertEquals(1,prestamo1.size)
        Assertions.assertEquals(1,prestamo2.size)
    }

    @Test
    fun findByBookTest(){
        val publisher = Publisher(1,"Santillana")
        val publisher2 = Publisher(2,"JuanSe")
        entityManager.persist(publisher)
        entityManager.persist(publisher2)

        val book1 = Book("1","EstrellasFeas","123",10,publisher)
        val book2 = Book("2","EstrellasLindas","456",10,publisher2)
        entityManager.persist(book1)
        entityManager.persist(book2)

        val user1 = User("1", "luis","gomez")
        val user2 = User("2", "juan","lucho")
        entityManager.persist(user1)
        entityManager.persist(user2)

        val date = Date(2021,9,28)

        val borrow1 = Borrow(1,date,book1,user1)
        val borrow2 = Borrow(2,date,book2,user2)

        val prestamo1 = borrowRepository.findByBook("1")
        val prestamo2 = borrowRepository.findByBook("2")

        Assertions.assertEquals(1,prestamo1.size)
        Assertions.assertEquals(1,prestamo2.size)
    }
}