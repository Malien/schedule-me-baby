package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.entity.User
import ua.edu.ukma.dudes.scheduleMeBaby.repository.UserRepository

class MyUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByLogin(username)
            ?: throw UsernameNotFoundException("User Not Found: $username")
        return org.springframework.security.core.userdetails.User(
            username,
            user.password,
            mutableListOf()
        )
    }
}