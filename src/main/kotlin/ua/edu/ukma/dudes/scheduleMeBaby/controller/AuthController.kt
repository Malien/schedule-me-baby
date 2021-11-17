package ua.edu.ukma.dudes.scheduleMeBaby.controller

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
@RequestMapping("/auth")
class AuthController(val authService: AuthService) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody credentials: UserCredentials): ResponseEntity<UserDTO> {
        return try {
            val authUserDTO: AuthUserDTO = authService.login(credentials)
            ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, JWT_TOKEN_PREFIX + authUserDTO.jwtToken)
                .body(authUserDTO.userDTO)
        } catch (e: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
}