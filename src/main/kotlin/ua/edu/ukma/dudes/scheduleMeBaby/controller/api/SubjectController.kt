package ua.edu.ukma.dudes.scheduleMeBaby.controller.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.SubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.CreateSubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.SubjectService
import ua.edu.ukma.dudes.scheduleMeBaby.service.UpdateSubjectDTO
import javax.validation.Valid

private val CONFIDENTIAL_MARKER: Marker = MarkerFactory.getMarker("CONFIDENTIAL")

@RestController
@RequestMapping("/subject")
@Tag(name = "Subject", description = "Subject related APIs")
class SubjectController(private val subjectService: SubjectService) {

    val logger = LoggerFactory.getLogger(SubjectController::class.java)

    @Operation(summary = "Retrieve all subjects")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200", description = "All subjects", content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = SubjectDTO::class))
            )]
        )]
    )
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    fun getAllSubjects(): Iterable<SubjectDTO> {
        logger.info(CONFIDENTIAL_MARKER, "GET /subject getAllSubjects")
        return subjectService.findAllSubjects()
    }

    @Operation(summary = "Retrieve subject by it's id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Found subject", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SubjectDTO::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "Not found", content = [Content()])
        ]
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    fun getSubjectById(@PathVariable id: Long): SubjectDTO {
        MDC.put("subjectRequest", id.toString())
        logger.info("GET /subject/$id findSubjectById")
        return subjectService.findSubjectById(id).orElseThrow { NotFoundException("Subject not found with id: $id") }
    }

    @Operation(summary = "Create new subject")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Created subject", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SubjectDTO::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "Subject name cannot be blank", content = [Content()]),
        ]
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = CreateSubjectDTO::class)
        )]
    )
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    fun createSubject(@Valid @RequestBody request: CreateSubjectDTO): SubjectDTO {
        logger.info(request.name)
        val subj = subjectService.createSubject(request)
        MDC.put("item.id", subj.id.toString())
        logger.info("POST /subject createSubject")
        return subj
    }

    @Operation(summary = "Update existing subject")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Updated subject", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SubjectDTO::class)
                )]
            ),
            ApiResponse(responseCode = "404", description = "Subject cannot be found", content = [Content()]),
            ApiResponse(responseCode = "400", description = "Subject name cannot be blank", content = [Content()]),
        ]
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = UpdateSubjectDTO::class)
        )]
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateSubject(@PathVariable id: Long, @Valid @RequestBody request: UpdateSubjectDTO): SubjectDTO {
        MDC.put("subjectRequest", id.toString())
        logger.info("POST /subject createSubject")
        return subjectService.updateSubject(id, request)
    }

    @Operation(summary = "Delete existing subject")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Deleted subject", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Subject cannot be found", content = [Content()]),
        ]
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteSubjectById(@PathVariable id: Long) {
        MDC.put("subjectRequest", id.toString())
        logger.info("DELETE /subject/$id deleteSubjectById")
        subjectService.deleteSubjectById(id)
    }
}