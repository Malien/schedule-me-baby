package ua.edu.ukma.dudes.scheduleMeBaby.security

import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
@Profile("prod")
class WebSecurityConfigProd : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http!!
        http.authorizeRequests()
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated()
    }
}