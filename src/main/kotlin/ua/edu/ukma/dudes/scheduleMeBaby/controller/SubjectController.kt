package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.SubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.SubjectService
import javax.validation.Valid

private val CONFIDENTIAL_MARKER: Marker = MarkerFactory.getMarker("CONFIDENTIAL")

@RestController
@RequestMapping("/subject")
class SubjectController(private val subjectService: SubjectService) {

    val logger = LoggerFactory.getLogger(SubjectController::class.java)

    @GetMapping("/")
    fun getAllSubjects(): Iterable<SubjectDTO> {
        logger.info(CONFIDENTIAL_MARKER, "GET /subject getAllSubjects")
        return subjectService.findAllSubjects()
    }

    @GetMapping("/{id}")
    fun getSubjectById(@PathVariable id: Long): SubjectDTO {
        MDC.put("subjectRequest", id.toString())
        logger.info("GET /subject/$id findSubjectById")
        val optional = subjectService.findSubjectById(id)
        return if (optional.isPresent)
            optional.get()
        else
            throw NotFoundException("Subject not found with id: $id")
    }

    @PostMapping("/")
    fun createSubject(@Valid @RequestBody subjectDTO: SubjectDTO): SubjectDTO {
        val subj = subjectService.createSubject(subjectDTO)
        MDC.put("subjectRequest", subj.id.toString())
        logger.info("POST /subject createSubject")
        return subj
    }

    @PutMapping("/")
    fun updateSubject(@Valid @RequestBody subjectDTO: SubjectDTO): SubjectDTO {
        MDC.put("subjectRequest", subjectDTO.id.toString())
        logger.info("POST /subject createSubject")
        return subjectService.updateSubject(subjectDTO)
    }

    @DeleteMapping("/{id}")
    fun deleteSubjectById(@PathVariable id: Long) {
        MDC.put("subjectRequest", id.toString())
        logger.info("DELETE /subject/$id deleteSubjectById")
        subjectService.deleteSubjectById(id)
    }
}