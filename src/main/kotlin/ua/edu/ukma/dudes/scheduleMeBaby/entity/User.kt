package ua.edu.ukma.dudes.scheduleMeBaby.entity

import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(name = "login", unique = true, nullable = false)
    val login: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.DETACH])
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableSet<Role> = mutableSetOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null
}