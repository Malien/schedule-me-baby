package ua.edu.ukma.dudes.scheduleMeBaby.controller.pages

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/")
@Tag(name = "Schedule", description = "Schedule specific APIs")
class SchedulePagesController {

    @GetMapping("/")
    fun books(): String {
        return "schedule"
    }
}