package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.Publisher
import co.edu.eam.disenosoftware.libreria.repositories.PublisherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class PublisherServices {

    @Autowired
    lateinit var publisherRepository: PublisherRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createPublisher(publisher: Publisher) {
        val publisherById = publisherRepository.find(publisher.code)

        if(publisherById != null){
            throw BusinessException("This Publisher already exists")
        }
        publisherRepository.create(publisher)
    }
}