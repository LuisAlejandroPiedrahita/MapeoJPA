package co.edu.eam.disenosoftware.libreria.controllers

import co.edu.eam.disenosoftware.libreria.modelos.entities.Borrow
import co.edu.eam.disenosoftware.libreria.modelos.entities.User
import co.edu.eam.disenosoftware.libreria.modelos.request.BorrowRequest
import co.edu.eam.disenosoftware.libreria.servicios.BorrowServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController

@RequestMapping("/borrows")
class BorrowController {

    @Autowired
    lateinit var borrowServices: BorrowServices

    @PostMapping
    fun createBorrowBook(@RequestBody borrowRequest: BorrowRequest){
        borrowServices.createBorrow(borrowRequest)
    }

    @DeleteMapping("{id}")
    fun deleteBorrow(@PathVariable("id")idBorrow:Int){
        borrowServices.returnBook(idBorrow)
    }
}