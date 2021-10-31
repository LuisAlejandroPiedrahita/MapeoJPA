package co.edu.eam.disenosoftware.libreria.modelos.entities

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.Column
import javax.persistence.ManyToOne
import javax.persistence.JoinColumn
import java.io.Serializable

@Entity
@Table(name = "author_book")

data class AuthorBook(

    @Id
    @Column(name = "author_book_id")
    val id: Int,

    @ManyToOne
    @JoinColumn(name = "book_id")
    val book: Book,

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: Author,

    ):Serializable