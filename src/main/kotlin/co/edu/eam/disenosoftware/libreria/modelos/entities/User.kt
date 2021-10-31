package co.edu.eam.disenosoftware.libreria.modelos.entities

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Column
import javax.persistence.Id
import java.io.Serializable

@Entity
@Table(name = "User")

data class User(

    @Id
    @Column(name = "user_id")
    val identification: String,

    @Column(name = "user_name")
    var name: String,

    @Column(name = "user_lastname")
    var lastName: String,

):Serializable