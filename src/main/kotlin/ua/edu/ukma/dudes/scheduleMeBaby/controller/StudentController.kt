package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.StudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.StudentService

@RestController
@RequestMapping("/student")
class StudentController(private val studentService: StudentService) {

    private val logger: Logger = LoggerFactory.getLogger(StudentController::class.java)
    private val CONFIDENTIAL_MARKER: Marker = MarkerFactory.getMarker("CONFIDENTIAL")

    @GetMapping("/")
    fun getStudents(): ResponseEntity<Iterable<StudentDTO>> {
        logger.info(CONFIDENTIAL_MARKER, "/student/ getStudents")
        return ResponseEntity.ok(studentService.findAllStudents())
    }

    @GetMapping("/{id}")
    fun getStudentByID(@PathVariable id: Long): ResponseEntity<StudentDTO> {
        logger.info("/student/$id getStudentByID")
        val optional = studentService.findStudentByID(id)
        return if (optional.isPresent)
            ResponseEntity.ok(optional.get())
        else
            ResponseEntity.notFound().build()
    }

    @PostMapping("/")
    fun createStudent(@RequestBody student: StudentDTO): StudentDTO {
        logger.info("/student/ createStudent")
        return studentService.createStudent(student)
    }

    @PutMapping("/")
    fun updateStudent(@RequestBody student: StudentDTO): StudentDTO {
        logger.info("/student/ updateStudent")
        return studentService.updateStudent(student)
    }

    @DeleteMapping("/{id}")
    fun deleteStudentByID(@PathVariable id: Long) {
        logger.info("/student/$id deleteStudentByID")
        return studentService.deleteStudentByID(id)
    }
}