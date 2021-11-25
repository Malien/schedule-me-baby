package ua.edu.ukma.dudes.scheduleMeBaby.controller.pages

import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal

@Controller
class AuthPagesController {

    private val logger = LoggerFactory.getLogger(AuthPagesController::class.java)

    @GetMapping("/login")
    fun login(model: Model, principal: Principal?): String {
        logger.info("Principal is $principal")
        if (principal != null && principal is UsernamePasswordAuthenticationToken) {
            model.addAttribute("username", principal.name)
        }
        return "login"
    }

}