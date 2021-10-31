package co.edu.eam.disenosoftware.libreria.modelos.entities

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Column
import javax.persistence.Id
import java.io.Serializable

@Entity
@Table( name = "publisher")

data class Publisher(

    @Id
    @Column(name = "publisher_code")
    val code: Int,

    @Column(name = "publisher_name")
    var name: String,

):Serializable