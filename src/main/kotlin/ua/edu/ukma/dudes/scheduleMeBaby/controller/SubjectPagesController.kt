package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import ua.edu.ukma.dudes.scheduleMeBaby.service.SubjectService

@Controller
@RequestMapping("/subjects")
class SubjectPagesController(private val subjectService: SubjectService) {



    @GetMapping("")
    fun listSubjects(model: Model): String {
        model.addAttribute("subjects", subjectService.findAllSubjects())
        return "subjects"
    }

    @GetMapping("/{id}")
    fun editSubject(@PathVariable id: Long, model: Model): String {
        val subject = subjectService.findSubjectById(id);
        model.addAttribute("subject", subject.get())
        return "subjects-edit"
    }
}