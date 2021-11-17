package ua.edu.ukma.dudes.scheduleMeBaby.security.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ua.edu.ukma.dudes.scheduleMeBaby.entity.User

class UserPrincipal(val userEntity: User) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = userEntity.roles

    override fun getPassword(): String = userEntity.password

    override fun getUsername(): String = userEntity.login

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

}