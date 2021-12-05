package ua.edu.ukma.dudes.scheduleMeBaby.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
class PasswordEncryption(private val userDetailsService: UserDetailsService) {
    @Bean
    fun encoder(): PasswordEncoder? {
        return BCryptPasswordEncoder(8)
    }

    @Bean
    fun authProvider(): DaoAuthenticationProvider? {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }

}