package ua.edu.ukma.dudes.scheduleMeBaby.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ua.edu.ukma.dudes.scheduleMeBaby.service.generateToken
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter internal constructor(
    authenticationManager: AuthenticationManager,
    private val objectMapper: ObjectMapper
) : UsernamePasswordAuthenticationFilter() {
    init {
        setAuthenticationManager(authenticationManager)
    }

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        val userCredentials = objectMapper.readValue(request.inputStream, UserCredentials::class.java)
        val authToken = UsernamePasswordAuthenticationToken(
            userCredentials.login, userCredentials.password, mutableListOf()
        )
        return authenticationManager.authenticate(authToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain, auth: Authentication
    ) {
        SecurityContextHolder.getContext().authentication = auth
        val authenticatedUser = auth.principal as User
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(objectMapper.writeValueAsString(authenticatedUser))
        response.setHeader(HttpHeaders.AUTHORIZATION, generateToken(authenticatedUser))
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
    }

    private data class UserCredentials(
        var login: String?,
        var password: String?
    )
}
