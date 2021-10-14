package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.StudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.StudentService
import javax.validation.Valid

@RestController
@RequestMapping("/student")
class StudentController(private val studentService: StudentService) {

    private val logger: Logger = LoggerFactory.getLogger(StudentController::class.java)
    private val CONFIDENTIAL_MARKER: Marker = MarkerFactory.getMarker("CONFIDENTIAL")

    @GetMapping("/")
    fun getStudents(): Iterable<StudentDTO> {
        logger.info(CONFIDENTIAL_MARKER, "/student/ getStudents")
        return studentService.findAllStudents()
    }

    @GetMapping("/{id}")
    fun getStudentById(@PathVariable id: Long): StudentDTO {
        logger.info("/student/$id getStudentById")
        val optional = studentService.findStudentById(id)
        return if (optional.isPresent)
            optional.get()
        else
            throw NotFoundException("Student not found with id: $id")
    }

    @PostMapping("/")
    fun createStudent(@Valid @RequestBody student: StudentDTO): StudentDTO {
        logger.info("/student/ createStudent")
        return studentService.createStudent(student)
    }

    @PutMapping("/")
    fun updateStudent(@Valid @RequestBody student: StudentDTO): StudentDTO {
        logger.info("/student/ updateStudent")
        return studentService.updateStudent(student)
    }

    @DeleteMapping("/{id}")
    fun deleteStudentById(@PathVariable id: Long) {
        logger.info("/student/$id deleteStudentById")
        return studentService.deleteStudentById(id)
    }
}