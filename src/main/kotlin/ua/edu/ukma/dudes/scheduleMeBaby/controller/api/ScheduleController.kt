package ua.edu.ukma.dudes.scheduleMeBaby.controller.api

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/schedule")
@Tag(name = "Schedule", description = "Schedule specific APIs")
class ScheduleController {

    @GetMapping("")
    fun books(): String {
        return "schedule"
    }
}