package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.SubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.SubjectService
import javax.validation.Valid

@RestController
@RequestMapping("/subject")
class SubjectController(private val subjectService: SubjectService) {

    @GetMapping("/")
    fun getAllSubjects(): Iterable<SubjectDTO> = subjectService.findAllSubjects()

    @GetMapping("/{id}")
    fun getSubjectById(@PathVariable id: Long): SubjectDTO {
        val optional = subjectService.findSubjectById(id)
        return if (optional.isPresent)
            optional.get()
        else
            throw NotFoundException("Subject not found with id: $id")
    }

    @PostMapping("/")
    fun createSubject(@Valid @RequestBody subjectDTO: SubjectDTO): SubjectDTO = subjectService.createSubject(subjectDTO)

    @PutMapping("/")
    fun updateSubject(@Valid @RequestBody subjectDTO: SubjectDTO): SubjectDTO = subjectService.updateSubject(subjectDTO)

    @DeleteMapping("/{id}")
    fun deleteSubjectById(@PathVariable id: Long) = subjectService.deleteSubjectById(id)
}