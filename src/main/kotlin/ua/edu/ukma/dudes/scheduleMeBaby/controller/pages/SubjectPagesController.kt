package ua.edu.ukma.dudes.scheduleMeBaby.controller.pages

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.controller.isAdmin
import ua.edu.ukma.dudes.scheduleMeBaby.dto.CreateSubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.UpdateSubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.SubjectService
import java.security.Principal
import javax.validation.Valid

@Controller
@RequestMapping("/subjects")
class SubjectPagesController(private val subjectService: SubjectService) {

    val logger = LoggerFactory.getLogger(SubjectPagesController::class.java)

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    fun listSubjects(
        @RequestParam(value = "filter", required = false) nameFilter: String?,
        @RequestParam(value = "error", required = false) error: String?,
        model: Model, principal: Principal?
    ): String {
        val isAdmin = principal?.isAdmin ?: false
        logger.info("List subjects: isAdmin=$isAdmin")
        model.addAttribute("isAdmin", isAdmin)
        model.addAttribute("subjects", subjectService.findAllSubjects(nameFilter ?: ""))
        model.addAttribute("subjectNameFilter", nameFilter)
        model.addAttribute("error", error)
        return "subjects"
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    fun newSubject(@Valid createSubjectDTO: CreateSubjectDTO, model: Model, principal: Principal?) =
        protectedAction(model, principal) {
            subjectService.createSubject(createSubjectDTO)
        }

    @PostMapping(path = ["/{id}"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    fun editSubject(
        @PathVariable id: Long,
        @Valid patch: UpdateSubjectDTO,
        model: Model,
        principal: Principal?
    ) = protectedAction(model, principal) {
        subjectService.updateSubject(id, patch)
    }

    @PostMapping(path = ["/{id}/delete"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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
