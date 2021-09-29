package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Subject
import ua.edu.ukma.dudes.scheduleMeBaby.service.SubjectService
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestController
@RequestMapping("/subject")
class SubjectController(private val subjectService: SubjectService) {

    @GetMapping("/all")
    fun getAllSubjects(): ResponseEntity<Iterable<Subject>> = ResponseEntity.ok(subjectService.findAllSubjects())

    @GetMapping("/{id}")
    fun getStudentById(@PathVariable id: Long): ResponseEntity<Subject> {
        val optional = subjectService.findSubjectById(id)
        return if (optional.isPresent)
            ResponseEntity.ok(optional.get())
        else
            ResponseEntity.notFound().build()
    }
}