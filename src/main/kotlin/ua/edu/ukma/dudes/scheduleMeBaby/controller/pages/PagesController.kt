package ua.edu.ukma.dudes.scheduleMeBaby.controller.pages

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PagesController {

    @GetMapping("/login")
    fun login(model: Model) = "login"

}