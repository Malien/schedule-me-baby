package ua.edu.ukma.dudes.scheduleMeBaby.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import ua.edu.ukma.dudes.scheduleMeBaby.service.getUsernameFromToken
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse)
            return
        }
        val username: String = getUsernameFromToken(token)
        val userDetails = userDetailsService.loadUserByUsername(username)
        val auth = UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
        )
        SecurityContextHolder.getContext().authentication = auth
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }
}
