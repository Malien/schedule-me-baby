package ua.edu.ukma.dudes.scheduleMeBaby.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.*
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.CreateTeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.TeacherService
import ua.edu.ukma.dudes.scheduleMeBaby.service.UpdateTeacherDTO
import javax.validation.Valid

private val CONFIDENTIAL_MARKER: Marker = MarkerFactory.getMarker("CONFIDENTIAL")

@RestController
@RequestMapping("/teacher")
@Tag(name = "Teacher", description = "Teacher entity APIs")
class TeacherController(private val teacherService: TeacherService) {

    private val logger: Logger = LoggerFactory.getLogger(TeacherController::class.java)

    @Operation(summary = "Retrieve all teachers")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200", description = "All teachers", content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = TeacherDTO::class))
            )]
        )]
    )
    @GetMapping("/")
    fun getTeachers(): Iterable<TeacherDTO> {
        logger.info(CONFIDENTIAL_MARKER, "/teacher/ getTeachers")
        return teacherService.findAllTeachers().map(Teacher::toDto)
    }

    @Operation(summary = "Retrieve teacher by it's id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Found teacher", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = TeacherDTO::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "Not found", content = [Content()])
        ]
    )
    @GetMapping("/{id}")
    fun getTeacherById(@PathVariable id: Long): TeacherDTO {
        MDC.put("teacherRequest", id.toString())
        logger.info("/teacher/$id getTeacherById")
        return teacherService
            .findTeacherById(id)
            .orElseThrow { NotFoundException("Cannot find teacher by id: $id") }
            .toDto()
    }

    @Operation(summary = "Create new teacher")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Created teacher", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = TeacherDTO::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "Teacher name cannot be blank", content = [Content()]),
        ]
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = TeacherDTO::class)
        )]
    )
    @PostMapping("/")
    fun createTeacher(@Valid @RequestBody request: CreateTeacherDTO): TeacherDTO {
        val teacher = teacherService.createTeacher(request)
        MDC.put("teacherRequest", teacher.id.toString())
        logger.info("/teacher/ createTeacher")
        return teacher
    }

    @Operation(summary = "Update existing teacher")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Updated teacher", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = TeacherDTO::class)
                )]
            ),
            ApiResponse(responseCode = "404", description = "Teacher cannot be found", content = [Content()]),
            ApiResponse(responseCode = "400", description = "Teacher name cannot be blank", content = [Content()]),
        ]
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = UpdateTeacherDTO::class)
        )]
    )
    @PatchMapping("/{id}")
    fun updateTeacher(@PathVariable id: Long, @Valid @RequestBody patch: UpdateTeacherDTO): TeacherDTO {
        MDC.put("teacherRequest", id.toString())
        logger.info("/teacher/ updateTeacher")
        return teacherService.updateTeacher(id, patch)
    }

    @Operation(summary = "Delete existing teacher")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Deleted teacher", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Teacher cannot be found", content = [Content()]),
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteTeacherById(@PathVariable id: Long) {
        MDC.put("teacherRequest", id.toString())
        logger.info("/teacher/$id deleteTeacherById")
        return teacherService.deleteTeacherById(id)
    }
}
