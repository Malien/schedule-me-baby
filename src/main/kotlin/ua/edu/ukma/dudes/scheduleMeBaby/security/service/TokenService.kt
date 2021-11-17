package ua.edu.ukma.dudes.scheduleMeBaby.security.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.UserPrincipal
import java.security.Key

private val TOKEN_KEY: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
const val JWT_TOKEN_PREFIX = "Bearer "

@Service
class TokenService {

    fun generateToken(authenticatedUser: UserPrincipal): String {
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
}