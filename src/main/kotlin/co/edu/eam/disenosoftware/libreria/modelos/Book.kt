package co.edu.eam.disenosoftware.libreria.modelos

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.Column
import javax.persistence.ManyToOne
import javax.persistence.JoinColumn
import java.io.Serializable

@Entity
@Table(name = "book")

data class Book(

    @Id
    @Column(name = "book_code")
    val code: String,

    @Column(name = "book_name")
    var name: String,

    @Column(name = "book_isbn")
    var isbn: String,

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    val publisher: Publisher,

):Serializable
