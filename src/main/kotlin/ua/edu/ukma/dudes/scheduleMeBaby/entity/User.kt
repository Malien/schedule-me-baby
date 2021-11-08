package ua.edu.ukma.dudes.scheduleMeBaby.entity

import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(name = "login", unique = true, nullable = false)
    val login: String,

    @Column(name = "password", nullable = false)
    val password: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null
}