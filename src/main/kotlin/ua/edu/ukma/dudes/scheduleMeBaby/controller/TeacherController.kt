package ua.edu.ukma.dudes.schedulemebaby.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.schedulemebaby.dto.CreateTeacherDTO
import ua.edu.ukma.dudes.schedulemebaby.entity.Teacher
import ua.edu.ukma.dudes.schedulemebaby.service.TeacherService
import java.util.*

@RestController
@RequestMapping("/teacher")
class TeacherController {

    // TODO just for example, move to ctor
    @Autowired
    private lateinit var teacherService: TeacherService

    @GetMapping("/")
    fun getStudents(): Iterable<Teacher> = teacherService.findAllTeachers()

    @GetMapping("/{id}")
    fun getStudentByID(@PathVariable id: Int): Optional<Teacher> = teacherService.findTeacherByID(id)

    @PostMapping("/")
    fun saveStudent(@RequestBody teacher: CreateTeacherDTO): Teacher =
        teacherService.saveTeacher(Teacher(name = teacher.name))

    @DeleteMapping("/{id}")
    fun deleteStudentByID(@PathVariable id: Int) = teacherService.deleteTeacherByID(id)
}
