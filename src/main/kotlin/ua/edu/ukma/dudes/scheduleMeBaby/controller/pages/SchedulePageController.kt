package ua.edu.ukma.dudes.scheduleMeBaby.controller.pages

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@Tag(name = "Schedule", description = "Schedule specific APIs")
class SchedulePageController {

    @GetMapping("/schedule")
    fun schedule(): String {
        return "schedule"
    }
}