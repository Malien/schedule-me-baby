package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.StudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.StudentService
import java.util.*

@RestController
@RequestMapping("/student")
class StudentController(private val studentService: StudentService) {

    @GetMapping("/")
    fun getStudents(): ResponseEntity<Iterable<StudentDTO>> = ResponseEntity.ok(studentService.findAllStudents())

    @GetMapping("/{id}")
    fun getStudentByID(@PathVariable id: Long): ResponseEntity<StudentDTO> {
        val optional = studentService.findStudentByID(id)
        return if (optional.isPresent)
            ResponseEntity.ok(optional.get())
        else
            ResponseEntity.notFound().build()
    }

    @PostMapping("/")
    fun saveStudent(@RequestBody student: StudentDTO): StudentDTO = studentService.saveStudent(student)

    @DeleteMapping("/{id}")
    fun deleteStudentByID(@PathVariable id: Long) = studentService.deleteStudentByID(id)
}