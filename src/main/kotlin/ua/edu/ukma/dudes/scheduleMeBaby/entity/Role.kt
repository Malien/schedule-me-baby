package ua.edu.ukma.dudes.scheduleMeBaby.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Entity
@Table(name="roles")
class Role(
    @JsonIgnore
    @Column(name = "role_name", nullable = false, unique = true)
    val roleName: String
) : GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    @JsonIgnore
    val roleId: Long? = null

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    val users: MutableSet<User> = mutableSetOf()

    override fun getAuthority() = roleName

    override fun toString(): String = roleName
}