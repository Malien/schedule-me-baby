package ua.edu.ukma.dudes.scheduleMeBaby.security.service

import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ua.edu.ukma.dudes.scheduleMeBaby.entity.User
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.UserPrincipal
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto.AuthUserDTO
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto.UserCredentials
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto.UserDTO
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto.toDto

@Service
@Profile("prod")
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService
) {

    @Transactional
    fun login(userCredentials: UserCredentials): AuthUserDTO {
        val authenticate = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(userCredentials.login, userCredentials.password)
        )
        val principal: UserPrincipal = authenticate.principal as UserPrincipal
        val token: String = tokenService.generateToken(principal)
        return AuthUserDTO( token, principal.userEntity.toDto() )
    }
}