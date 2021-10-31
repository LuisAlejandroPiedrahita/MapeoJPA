package co.edu.eam.disenosoftware.libreria.controllers

import co.edu.eam.disenosoftware.libreria.modelos.entities.Borrow
import co.edu.eam.disenosoftware.libreria.modelos.entities.User
import co.edu.eam.disenosoftware.libreria.servicios.BorrowServices
import co.edu.eam.disenosoftware.libreria.servicios.UserServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityNotFoundException

@RestController

@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userServices: UserServices

    @Autowired
    lateinit var borrowServices: BorrowServices

    @PostMapping
    fun createUser(@RequestBody user: User){
        userServices.createUser(user)
    }

    @GetMapping("/{id}/borrows")
    fun findBorrowsByUser(@PathVariable("id")idUsuario: String):List<Borrow>{
        val listBorrows = borrowServices.findByUser(idUsuario)
        return listBorrows
    }
}