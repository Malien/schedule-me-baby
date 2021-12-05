package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.security.Principal

val Principal.isAdmin: Boolean
    get() = run {
        val token = this as UsernamePasswordAuthenticationToken
        token.authorities.find { it.authority == "ROLE_ADMIN" } != null
    }

val WebRequest.path: String
    get() = run {
        val servletReq = this as ServletWebRequest
        servletReq.request.requestURI
    }
