package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.security.Principal

val Principal.isAdmin get() = run {
    val token = this as UsernamePasswordAuthenticationToken
    token.authorities.find { it.authority == "ROLE_ADMIN" } != null
}
