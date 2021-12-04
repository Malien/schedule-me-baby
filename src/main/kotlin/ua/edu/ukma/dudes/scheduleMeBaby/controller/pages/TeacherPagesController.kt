package ua.edu.ukma.dudes.scheduleMeBaby.controller.pages

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import ua.edu.ukma.dudes.scheduleMeBaby.dto.CreateTeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.UpdateTeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.TeacherService
import java.security.Principal

@Controller
@RequestMapping("/teachers")
class TeacherPagesController(private val teacherService: TeacherService) {

    val logger = LoggerFactory.getLogger(TeacherPagesController::class.java)

    @GetMapping("")
    fun listTeachers(model: Model, principal: Principal?): String {
        val isAdmin = principal != null && principal is UsernamePasswordAuthenticationToken && principal.isAdmin
        logger.info("List teachers: isAdmin=$isAdmin")
        model.addAttribute("isAdmin", isAdmin)
        model.addAttribute("teachers", teacherService.findAllTeachers())
        return "teachers"
    }

    @GetMapping("/{id}")
    fun editTeacherPage(@PathVariable id: Long, model: Model, principal: Principal?): String {
        val isAdmin = principal != null && principal is UsernamePasswordAuthenticationToken && principal.isAdmin
        if (!isAdmin) {
            model["error"] = "You do not have permissions to access teacher edit page"
            model.addAttribute("teachers", teacherService.findAllTeachers())
            return "teachers"
        }
        val teacher = teacherService.findTeacherById(id);
        model.addAttribute("teacher", teacher.get())
        return "teachers-edit"
    }

    @PostMapping(path = ["/"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun newTeacher(createTeacherDTO: CreateTeacherDTO, model: Model, principal: Principal?) =
        protectedAction(model, principal) {
            teacherService.createTeacher(createTeacherDTO)
        }

    @PostMapping(path = ["/{id}"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun editTeacher(
        @PathVariable id: Long,
        patch: UpdateTeacherDTO,
        model: Model,
        principal: Principal?
    ) = protectedAction(model, principal) {
        teacherService.updateTeacher(id, patch)
    }

    @PostMapping(path = ["/{id}/delete"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun deleteTeacher(@PathVariable id: Long, model: Model, principal: Principal?) =
        protectedAction(model, principal) {
            teacherService.deleteTeacherById(id)
        }

    fun protectedAction(model: Model, principal: Principal?, block: () -> Unit): String {
        val isAdmin = principal != null && principal is UsernamePasswordAuthenticationToken && principal.isAdmin
        return if (!isAdmin) {
            model["error"] = "You do not have enough permissions to edit a teacher"
            model.addAttribute("teachers", teacherService.findAllTeachers())
            "teachers"
        } else try {
            block()
            "redirect:/teachers"
        } catch (e: Exception) {
            logger.error(e.message)
            model["error"] = e.message ?: "Unexpected error occurred"
            model.addAttribute("teachers", teacherService.findAllTeachers())
            "teachers"
        }
    }
}
