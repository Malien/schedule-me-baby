package ua.edu.ukma.dudes.scheduleMeBaby.entity

import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(name = "name", nullable = false)
    val name: String,

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
    @Column(name = "user_id", nullable = false)
    var id: Long? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "students_groups",
        joinColumns = [JoinColumn(name="student_id")],
        inverseJoinColumns = [JoinColumn(name="group_id")]
    )
    var studentGroups: MutableSet<Group> = mutableSetOf()
}