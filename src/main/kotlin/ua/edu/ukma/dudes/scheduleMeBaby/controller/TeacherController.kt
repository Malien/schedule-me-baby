package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import ua.edu.ukma.dudes.scheduleMeBaby.service.TeacherService
import java.util.*

@RestController
@RequestMapping("/teacher")
class TeacherController(private val teacherService: TeacherService) {

    private val logger: Logger = LoggerFactory.getLogger(TeacherController::class.java)
    private val CONFIDENTIAL_MARKER: Marker = MarkerFactory.getMarker("CONFIDENTIAL")

    @GetMapping("/")
    fun getTeachers(): Iterable<Teacher> {
        logger.info(CONFIDENTIAL_MARKER, "/teacher/ getTeachers")
        return teacherService.findAllTeachers()
    }

    @GetMapping("/{id}")
    fun getTeacherById(@PathVariable id: Long): Optional<Teacher> {
        logger.info("/teacher/$id getTeacherById")
        return teacherService.findTeacherById(id)
    }

    @PostMapping("/")
    fun createTeacher(@RequestBody teacher: TeacherDTO): TeacherDTO {
        logger.info("/teacher/ createTeacher")
        return teacherService.createTeacher(teacher)
    }

    @PutMapping("/")
    fun updateTeacher(@RequestBody teacher: TeacherDTO): TeacherDTO {
        logger.info("/teacher/ updateTeacher")
        return teacherService.updateTeacher(teacher)
    }

    @DeleteMapping("/{id}")
    fun deleteTeacherById(@PathVariable id: Long) {
        logger.info("/teacher/$id deleteTeacherById")
        return teacherService.deleteTeacherById(id)
    }
}
