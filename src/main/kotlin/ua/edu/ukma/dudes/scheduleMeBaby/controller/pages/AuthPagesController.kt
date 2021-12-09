package ua.edu.ukma.dudes.scheduleMeBaby.controller.pages

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import ua.edu.ukma.dudes.scheduleMeBaby.security.service.AuthService
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto.UserCredentials
import java.security.Principal
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@Controller
class AuthPagesController(private val authService: AuthService) {

    private val logger = LoggerFactory.getLogger(AuthPagesController::class.java)

    @GetMapping("/login")
    fun loginPage(model: Model, principal: Principal?): String {
        logger.info("Principal is $principal")
        if (principal != null && principal is UsernamePasswordAuthenticationToken) {
            model.addAttribute("username", principal.name)
        }
        return "login"
    }

    @PostMapping(path = ["/login"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun login(@Valid credentials: UserCredentials, response: HttpServletResponse, model: Model): String {
        try {
            val token = authService.login(credentials)
            response.setHeader("Set-Cookie", "token=${token.token}; HttpOnly; SameSite=Lax")
            return "redirect:schedule"
        } catch (e: BadCredentialsException) {
            logger.error("Bad credentials encountered: $e")
            model["badCredentials"] = true
            return "login"
        }
    }

}