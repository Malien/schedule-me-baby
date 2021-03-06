package ua.edu.ukma.dudes.scheduleMeBaby.controller.api

import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ua.edu.ukma.dudes.scheduleMeBaby.security.service.AuthService
import ua.edu.ukma.dudes.scheduleMeBaby.security.service.JWT_TOKEN_PREFIX
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto.AuthUserDTO
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto.UserCredentials
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto.UserDTO
import javax.validation.Valid

@RestController
@Profile("prod")
@RequestMapping("/auth")
class AuthController(val authService: AuthService) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody credentials: UserCredentials): ResponseEntity<AuthUserDTO> = try {
        ResponseEntity.ok().body(authService.login(credentials))
    } catch (e: BadCredentialsException) {
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }
}