package ua.edu.ukma.dudes.scheduleMeBaby.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.User
import java.security.Key

private val TOKEN_KEY: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

fun generateToken(authenticatedUser: User): String {
    return Jwts.builder()
        .setSubject(authenticatedUser.username)
        .signWith(TOKEN_KEY)
        .compact()
}

fun getUsernameFromToken(jwt: String?): String {
    return Jwts.parserBuilder()
        .setSigningKey(TOKEN_KEY)
        .build()
        .parseClaimsJws(jwt)
        .body
        .subject
}
