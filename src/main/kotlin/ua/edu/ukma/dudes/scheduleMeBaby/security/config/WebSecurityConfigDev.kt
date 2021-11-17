package ua.edu.ukma.dudes.scheduleMeBaby.security.config

import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke

@Profile("dev")
@EnableWebSecurity
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
