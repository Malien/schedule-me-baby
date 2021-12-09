package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import ua.edu.ukma.dudes.scheduleMeBaby.entity.User
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.UserPrincipal
import java.security.Principal

val Principal?.isAdmin: Boolean
    get() {
        if (this == null) return false
        val token = this as UsernamePasswordAuthenticationToken
        return token.authorities.find { it.authority == "ROLE_ADMIN" } != null
    }

val Principal.user: User
    get() {
        val token = this as UsernamePasswordAuthenticationToken
        val principal = token.principal as UserPrincipal
        return principal.userEntity
    }

val WebRequest.path: String
    get() {
        val servletReq = this as ServletWebRequest
        return servletReq.request.requestURI
    }
