package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.SubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.SubjectService

@RestController
@RequestMapping("/subject")
class SubjectController(private val subjectService: SubjectService) {

    @GetMapping("/all")
    fun getAllSubjects(): ResponseEntity<Iterable<SubjectDTO>> = ResponseEntity.ok(subjectService.findAllSubjects())

    @GetMapping("/{id}")
    fun getSubjectById(@PathVariable id: Long): ResponseEntity<SubjectDTO> {
        val optional = subjectService.findSubjectById(id)
        return if (optional.isPresent)
            ResponseEntity.ok(optional.get())
        else
            ResponseEntity.notFound().build()
    }

    @PostMapping("/")
    fun saveSubject(@RequestBody subjectDTO: SubjectDTO): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(subjectService.saveSubject(subjectDTO))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteSubjectById(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            subjectService.deleteSubjectById(id)
            ResponseEntity.ok().build()
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}