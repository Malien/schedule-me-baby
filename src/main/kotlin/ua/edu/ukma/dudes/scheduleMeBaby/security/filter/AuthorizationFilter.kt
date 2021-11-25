package ua.edu.ukma.dudes.scheduleMeBaby.security.filter

import io.jsonwebtoken.security.SignatureException
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.filter.OncePerRequestFilter
import ua.edu.ukma.dudes.scheduleMeBaby.security.service.JWT_TOKEN_PREFIX
import ua.edu.ukma.dudes.scheduleMeBaby.security.service.TokenService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Profile("prod")
class AuthorizationFilter(
    private val userDetailsService: UserDetailsService,
    private val tokenService: TokenService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)
        if (header == null || !header.startsWith(JWT_TOKEN_PREFIX)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse)
            return
        }
        val token: String = header.replace(JWT_TOKEN_PREFIX, "")

        try {
            val username: String = tokenService.getUsernameFromToken(token)
            val userDetails = userDetailsService.loadUserByUsername(username)
            val auth = UsernamePasswordAuthenticationToken(
                userDetails, userDetails.password, userDetails.authorities
            )
            SecurityContextHolder.getContext().authentication = auth
        } catch (e: UsernameNotFoundException) {
            SecurityContextHolder.clearContext()
        } catch (e: SignatureException) {
            SecurityContextHolder.clearContext()
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }
}
