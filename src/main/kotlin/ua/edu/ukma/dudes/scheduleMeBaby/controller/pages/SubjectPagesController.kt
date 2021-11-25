package ua.edu.ukma.dudes.scheduleMeBaby.controller.pages

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.service.CreateSubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.SubjectService
import ua.edu.ukma.dudes.scheduleMeBaby.service.UpdateSubjectDTO
import java.security.Principal

@Controller
@RequestMapping("/subjects")
class SubjectPagesController(private val subjectService: SubjectService) {

    val logger = LoggerFactory.getLogger(SubjectPagesController::class.java)

    @GetMapping("")
    fun listSubjects(model: Model, principal: Principal?): String {
        val isAdmin = principal != null && principal is UsernamePasswordAuthenticationToken && principal.isAdmin
        logger.info("List subjects: isAdmin=$isAdmin")
        model.addAttribute("isAdmin", isAdmin)
        model.addAttribute("subjects", subjectService.findAllSubjects())
        return "subjects"
    }

    @GetMapping("/{id}")
    fun editSubjectPage(@PathVariable id: Long, model: Model, principal: Principal?): String {
        val isAdmin = principal != null && principal is UsernamePasswordAuthenticationToken && principal.isAdmin
        if (!isAdmin) {
            model["error"] = "You do not have permissions to access subject edit page"
            return "subjects"
        }
        val subject = subjectService.findSubjectById(id);
        model.addAttribute("subject", subject.get())
        return "subjects-edit"
    }

    @PostMapping(path = ["/"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun newSubject(createSubjectDTO: CreateSubjectDTO, model: Model, principal: Principal?) =
        protectedAction(model, principal) {
            subjectService.createSubject(createSubjectDTO)
        }

    @PostMapping(path = ["/{id}"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun editSubject(
        @PathVariable id: Long,
        patch: UpdateSubjectDTO,
        model: Model,
        principal: Principal?
    ) = protectedAction(model, principal) {
        subjectService.updateSubject(id, patch)
    }

    @PostMapping(path = ["/{id}/delete"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun deleteSubject(@PathVariable id: Long, model: Model, principal: Principal?) =
        protectedAction(model, principal) {
            subjectService.deleteSubjectById(id)
        }

    fun protectedAction(model: Model, principal: Principal?, block: () -> Unit): String {
        val isAdmin = principal != null && principal is UsernamePasswordAuthenticationToken && principal.isAdmin
        return if (!isAdmin) {
            model["error"] = "You do not have enough permissions to edit a subject"
            "subjects"
        } else try {
            block()
            "redirect:/subjects"
        } catch (e: Exception) {
            logger.error(e.message)
            model["error"] = e.message ?: "Unexpected error occurred"
            "subjects"
        }
    }
}

val UsernamePasswordAuthenticationToken.isAdmin get() = authorities.find { it.authority == "ROLE_ADMIN" } != null
