package co.edu.eam.disenosoftware.libreria.controllers

import co.edu.eam.disenosoftware.libreria.modelos.entities.Book
import co.edu.eam.disenosoftware.libreria.modelos.entities.User
import co.edu.eam.disenosoftware.libreria.servicios.BookServices
import co.edu.eam.disenosoftware.libreria.servicios.BorrowServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController

@RequestMapping("/books")
class BookController {

    @Autowired
    lateinit var bookServices: BookServices

    @Autowired
    lateinit var borrowServices: BorrowServices

    @PostMapping("/{code}")
    fun createBook(@PathVariable("code")idPublisher:Int ,@Validated @RequestBody book: Book){
        bookServices.createBook(book,idPublisher)
    }

    @PutMapping("/{codigo}")
    fun editBook(@PathVariable("codigo") codeBook:String, @RequestBody book: Book){
        book.code = codeBook
        bookServices.editBook(book)
    }
    @GetMapping("/{id}/users")
    fun findUsersByBook(@PathVariable("id")idBook: String):List<User>{
        val listUsers = borrowServices.findUserByBook(idBook)
        return listUsers
    }
}