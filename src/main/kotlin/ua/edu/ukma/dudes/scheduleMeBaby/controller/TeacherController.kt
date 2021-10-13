package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import ua.edu.ukma.dudes.scheduleMeBaby.service.TeacherService
import java.util.*

@RestController
@RequestMapping("/teacher")
class TeacherController(val teacherService: TeacherService) {

    @GetMapping("/")
    fun getTeachers(): Iterable<Teacher> = teacherService.findAllTeachers()

    @GetMapping("/{id}")
    fun getTeacherByID(@PathVariable id: Long): Optional<Teacher> = teacherService.findTeacherByID(id)

    @PostMapping("/")
    fun createTeacher(@RequestBody teacher: TeacherDTO): TeacherDTO = teacherService.createTeacher(teacher)

    @PutMapping("/")
    fun updateTeacher(@RequestBody teacher: TeacherDTO): TeacherDTO = teacherService.updateTeacher(teacher)

    @DeleteMapping("/{id}")
    fun deleteTeacherByID(@PathVariable id: Long) = teacherService.deleteTeacherByID(id)
}
