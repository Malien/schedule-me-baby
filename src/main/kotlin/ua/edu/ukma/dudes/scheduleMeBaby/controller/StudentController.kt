package ua.edu.ukma.dudes.scheduleMeBaby.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.slf4j.MarkerFactory
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.StudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.CreateStudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.StudentService
import ua.edu.ukma.dudes.scheduleMeBaby.service.UpdateStudentDTO
import javax.validation.Valid

private val CONFIDENTIAL_MARKER = MarkerFactory.getMarker("CONFIDENTIAL")

@RestController
@RequestMapping("/student")
@Tag(name = "Student", description = "Student specific APIs")
class StudentController(private val studentService: StudentService) {

    private val logger = LoggerFactory.getLogger(StudentController::class.java)

    @Operation(summary = "Get all students")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200", description = "All students", content = [Content(
                mediaType = "application/json",
                array = (ArraySchema(schema = Schema(implementation = StudentDTO::class)))
            )]
        )]
    )
    @GetMapping("/")
    fun getStudents(): Iterable<StudentDTO> {
        logger.info(CONFIDENTIAL_MARKER, "/student/ getStudents")
        return studentService.findAllStudents()
    }

    @Operation(summary = "Get student by id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Found student", content = [Content(
                    mediaType = "application/json",
                    array = (ArraySchema(schema = Schema(implementation = StudentDTO::class)))
                )]
            ),
            ApiResponse(responseCode = "404", description = "Student not found", content = [Content()])
        ]
    )
    @GetMapping("/{id}")
    fun getStudentById(@PathVariable id: Long): StudentDTO {
        MDC.put("item_id", id.toString())
        logger.info("/student/$id getStudentById")
        return studentService
            .findStudentById(id)
            .orElseThrow { NotFoundException("Student not found with id: $id") }
    }

    @Operation(summary = "Create Student")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Created student", content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = StudentDTO::class))
                )]
            ),
            ApiResponse(responseCode = "400", description = "Student's name cannot be blank", content = [Content()])
        ]
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = CreateStudentDTO::class)
        )]
    )
    @PostMapping("/")
    fun createStudent(@Valid @RequestBody student: CreateStudentDTO): StudentDTO {
        logger.info("/student/ createStudent")
        return studentService.createStudent(student)
    }

    @Operation(summary = "Update Student")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Updated student", content = [Content(
                    mediaType = "application/json",
                    array = (ArraySchema(schema = Schema(implementation = StudentDTO::class)))
                )]
            ),
            ApiResponse(responseCode = "400", description = "Student's name cannot be blank", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Student not found", content = [Content()])
        ]
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = UpdateStudentDTO::class)
        )]
    )
    @PatchMapping("/{id}")
    fun updateStudent(@PathVariable id: Long, @Valid @RequestBody student: UpdateStudentDTO): StudentDTO {
        MDC.put("item_id", id.toString())
        logger.info("/student/ updateStudent")
        return studentService.updateStudent(id, student)
    }

    @Operation(summary = "Delete Student")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Deleted student", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Student not found", content = [Content()])
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteStudentById(@PathVariable id: Long) {
        MDC.put("item_id", id.toString())
        logger.info("/student/$id deleteStudentById")
        return studentService.deleteStudentById(id)
    }
}