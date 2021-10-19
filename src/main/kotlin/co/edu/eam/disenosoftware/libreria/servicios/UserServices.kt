package co.edu.eam.disenosoftware.libreria.servicios

import co.edu.eam.disenosoftware.libreria.exceptions.BusinessException
import co.edu.eam.disenosoftware.libreria.modelos.User
import co.edu.eam.disenosoftware.libreria.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class UserServices {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createUser(user: User) {
        val userById = userRepository.find(user.identification?:"")

        if(userById != null){
            throw BusinessException("This User already exists")
        }
        userRepository.create(user)
    }
}