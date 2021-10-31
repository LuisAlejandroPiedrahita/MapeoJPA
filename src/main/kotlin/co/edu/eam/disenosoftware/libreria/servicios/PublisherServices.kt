package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.entities.Publisher
import co.edu.eam.disenosoftware.libreria.repositories.PublisherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PublisherServices {

    @Autowired
    lateinit var publisherRepository: PublisherRepository

    fun createPublisher(publisher: Publisher) {
        val publisherById = publisherRepository.find(publisher.code)

        if(publisherById != null){
            throw BusinessException("This Publisher already exists")
        }
        publisherRepository.create(publisher)
    }
}