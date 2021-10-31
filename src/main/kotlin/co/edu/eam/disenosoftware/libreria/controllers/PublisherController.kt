package co.edu.eam.disenosoftware.libreria.controllers

import co.edu.eam.disenosoftware.libreria.modelos.entities.Publisher
import co.edu.eam.disenosoftware.libreria.servicios.PublisherServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController


@RequestMapping("/publishers")
class PublisherController {

    @Autowired
    lateinit var publisherServices: PublisherServices

    @PostMapping
    fun createPublisher(@RequestBody  publisher: Publisher){
        publisherServices.createPublisher(publisher)
    }
}