package ua.edu.ukma.dudes.scheduleMeBaby.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.entity.User
import ua.edu.ukma.dudes.scheduleMeBaby.repository.UserRepository
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.UserPrincipal

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByLogin(username)
            ?: throw UsernameNotFoundException("User Not Found: $username")
        return UserPrincipal(user)
    }
}