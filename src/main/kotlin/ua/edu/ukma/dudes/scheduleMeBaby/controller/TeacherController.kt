package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.CreateTeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import ua.edu.ukma.dudes.scheduleMeBaby.service.TeacherService
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
    fun getStudentByID(@PathVariable id: Long): Optional<Teacher> = teacherService.findTeacherByID(id)

    @PostMapping("/")
    fun saveStudent(@RequestBody teacher: CreateTeacherDTO): Teacher =
        teacherService.saveTeacher(Teacher(name = teacher.name))

    @DeleteMapping("/{id}")
    fun deleteStudentByID(@PathVariable id: Long) = teacherService.deleteTeacherByID(id)
}
