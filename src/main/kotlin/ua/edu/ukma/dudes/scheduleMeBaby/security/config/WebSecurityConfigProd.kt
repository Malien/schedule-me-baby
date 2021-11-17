package ua.edu.ukma.dudes.scheduleMeBaby.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ua.edu.ukma.dudes.scheduleMeBaby.repository.UserRepository
import ua.edu.ukma.dudes.scheduleMeBaby.security.filter.AuthorizationFilter
import ua.edu.ukma.dudes.scheduleMeBaby.security.service.TokenService
import ua.edu.ukma.dudes.scheduleMeBaby.security.service.UserDetailsServiceImpl

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
                .antMatchers("/auth/login").permitAll()
                .anyRequest().authenticated()
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .addFilterBefore(
                    AuthorizationFilter(userDetailsService(), TokenService()),
                    UsernamePasswordAuthenticationFilter::class.java
                )
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        return UserDetailsServiceImpl(userRepository)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}
