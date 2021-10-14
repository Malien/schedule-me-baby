package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.StudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.StudentService

@RestController
@RequestMapping("/student")
class StudentController(private val studentService: StudentService) {

    val logger: Logger = LoggerFactory.getLogger(StudentController::class.java)

    @GetMapping("/")
    fun getStudents(): ResponseEntity<Iterable<StudentDTO>> {
        logger.info("getStudents")
        return ResponseEntity.ok(studentService.findAllStudents())
    }

    @GetMapping("/{id}")
    fun getStudentByID(@PathVariable id: Long): ResponseEntity<StudentDTO> {
        val optional = studentService.findStudentByID(id)
        return if (optional.isPresent)
            ResponseEntity.ok(optional.get())
        else
            ResponseEntity.notFound().build()
    }

    @PostMapping("/")
    fun createStudent(@RequestBody student: StudentDTO): StudentDTO = studentService.createStudent(student)

    @PutMapping("/")
    fun updateStudent(@RequestBody student: StudentDTO): StudentDTO = studentService.updateStudent(student)

    @DeleteMapping("/{id}")
    fun deleteStudentByID(@PathVariable id: Long) = studentService.deleteStudentByID(id)
}