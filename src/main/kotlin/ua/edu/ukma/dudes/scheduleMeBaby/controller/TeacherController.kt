package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.CreateTeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import ua.edu.ukma.dudes.scheduleMeBaby.service.TeacherService
import java.util.*

@RestController
@RequestMapping("/teacher")
class TeacherController(val teacherService: TeacherService) {

    @GetMapping("/")
    fun getTeachers(): Iterable<Teacher> = teacherService.findAllTeachers()

    @GetMapping("/{id}")
    fun getTeacherByID(@PathVariable id: Int): Optional<Teacher> = teacherService.findTeacherByID(id)

    @PostMapping("/")
    fun saveTeacher(@RequestBody teacher: CreateTeacherDTO): Teacher =
        teacherService.saveTeacher(Teacher(name = teacher.name))

    @DeleteMapping("/{id}")
    fun deleteTeacherByID(@PathVariable id: Int) = teacherService.deleteTeacherByID(id)
}
