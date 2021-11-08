package ua.edu.ukma.dudes.scheduleMeBaby.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import ua.edu.ukma.dudes.scheduleMeBaby.repository.UserRepository
import ua.edu.ukma.dudes.scheduleMeBaby.service.MyUserDetailsService

@Profile("prod")
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
class WebSecurityConfig(
    private val userRepository: UserRepository,
    private val objectMapper: ObjectMapper,
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(customLoginFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(
                AuthorizationFilter(userDetailsService()),
                AuthenticationFilter::class.java
            )
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        return MyUserDetailsService(userRepository)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    private fun customLoginFilter(): AuthenticationFilter {
        val authenticationFilter = AuthenticationFilter(
            authenticationManagerBean(), objectMapper
        )
        authenticationFilter.setRequiresAuthenticationRequestMatcher(
            AntPathRequestMatcher(
                "/login",
                HttpMethod.POST.name
            )
        )
        return authenticationFilter
    }
}
