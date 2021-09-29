package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Student
import ua.edu.ukma.dudes.scheduleMeBaby.service.StudentService
import java.util.*

@RestController
@RequestMapping("/student")
class StudentController {

    // TODO just for example, move to ctor
    @Autowired
    private lateinit var studentService: StudentService

    @GetMapping("/")
    fun getStudents(): MutableIterable<Student> = studentService.findAllStudents()

    @GetMapping("/{id}")
    fun getStudentByID(@PathVariable id: Int): Optional<Student> = studentService.findStudentByID(id)

    @PostMapping("/")
    fun saveStudent(@RequestBody student: Student): Student = studentService.saveStudent(student)

    @DeleteMapping("/{id}")
    fun deleteStudentByID(@PathVariable id: Int) = studentService.deleteStudentByID(id)
}