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
import ua.edu.ukma.dudes.scheduleMeBaby.controller.isAdmin
import ua.edu.ukma.dudes.scheduleMeBaby.dto.CreateSubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.UpdateSubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.SubjectService
import java.security.Principal

@Controller
@RequestMapping("/subjects")
class SubjectPagesController(private val subjectService: SubjectService) {

    val logger = LoggerFactory.getLogger(SubjectPagesController::class.java)

    @GetMapping("/")
    fun listSubjects(model: Model, principal: Principal?): String {
        val isAdmin = principal?.isAdmin ?: false
        logger.info("List subjects: isAdmin=$isAdmin")
        model.addAttribute("isAdmin", isAdmin)
        model.addAttribute("subjects", subjectService.findAllSubjects())
        return "subjects"
    }

    @GetMapping("/{id}")
    fun editSubjectPage(@PathVariable id: Long, model: Model, principal: Principal?): String {
        val isAdmin = principal?.isAdmin ?: false
        if (!isAdmin) {
            model["error"] = "You do not have permissions to access subject edit page"
            model.addAttribute("subjects", subjectService.findAllSubjects())
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
        val isAdmin = principal?.isAdmin ?: false
        return if (!isAdmin) {
            model["error"] = "You do not have enough permissions to edit a subject"
            model.addAttribute("subjects", subjectService.findAllSubjects())
            "subjects"
        } else try {
            block()
            "redirect:/subjects"
        } catch (e: Exception) {
            logger.error(e.message)
            model["error"] = e.message ?: "Unexpected error occurred"
            model.addAttribute("subjects", subjectService.findAllSubjects())
            "subjects"
        }
    }
}
