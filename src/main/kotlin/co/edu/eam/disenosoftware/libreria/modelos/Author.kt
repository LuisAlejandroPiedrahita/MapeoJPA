package co.edu.eam.disenosoftware.libreria.modelos

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.Column
import java.io.Serializable

@Entity
@Table(name = "author")

data class Author(

    @Id
    @Column(name = "author_id")
    val id: Int,

    @Column(name = "author_name")
    var name: String,

    @Column(name = "author_lastname")
    var lastName: String,

):Serializable

