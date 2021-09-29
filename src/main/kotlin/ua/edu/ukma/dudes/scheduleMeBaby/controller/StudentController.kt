package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Student
import ua.edu.ukma.dudes.scheduleMeBaby.service.StudentService
import java.util.*

@RestController
@RequestMapping("/student")
class StudentController(private val studentService: StudentService) {
    @GetMapping("/")
    fun getStudents(): MutableIterable<Student> = studentService.findAllStudents()

    @GetMapping("/{id}")
    fun getStudentByID(@PathVariable id: Int): Optional<Student> = studentService.findStudentByID(id)
}