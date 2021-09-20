package co.edu.eam.disenosoftware.libreria.modelos

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.Column
import javax.persistence.ManyToOne
import javax.persistence.JoinColumn
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "borrow")

data class Borrow(

    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "borrow_date")
    var dateTime: Date,

    @ManyToOne
    @JoinColumn(name = "book_id")
    val book: Book,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

):Serializable