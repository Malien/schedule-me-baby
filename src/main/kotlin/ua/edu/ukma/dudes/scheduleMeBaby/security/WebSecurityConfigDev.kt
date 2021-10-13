package ua.edu.ukma.dudes.scheduleMeBaby.security

import org.jetbrains.annotations.PropertyKey
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke

@EnableWebSecurity
@ConditionalOnExpression("#{environment.getProperty('spring.profiles.active').contains('dev')")
class WebSecurityConfigDev : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http {
            httpBasic {}
            authorizeRequests {
                authorize("/**", permitAll)
            }
            csrf {
                disable()
            }
        }
    }
}
