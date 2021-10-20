package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.slf4j.*
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import ua.edu.ukma.dudes.scheduleMeBaby.service.TeacherService
import java.util.*
import javax.validation.Valid

private val CONFIDENTIAL_MARKER: Marker = MarkerFactory.getMarker("CONFIDENTIAL")

@RestController
@RequestMapping("/teacher")
class TeacherController(private val teacherService: TeacherService) {

    private val logger: Logger = LoggerFactory.getLogger(TeacherController::class.java)

    @GetMapping("/")
    fun getTeachers(): Iterable<Teacher> {
        logger.info(CONFIDENTIAL_MARKER, "/teacher/ getTeachers")
        return teacherService.findAllTeachers()
    }

    @GetMapping("/{id}")
    fun getTeacherById(@PathVariable id: Long): Optional<Teacher> {
        logger.info("/teacher/$id getTeacherById")
        MDC.put("teacherRequest", id.toString())
        return teacherService.findTeacherById(id)
    }

    @PostMapping("/")
    fun createTeacher(@Valid @RequestBody teacher: TeacherDTO): TeacherDTO {
        val teacher = teacherService.createTeacher(teacher)
        MDC.put("teacherRequest", teacher.id.toString())
        logger.info("/teacher/ createTeacher")
        return teacher
    }

    @PutMapping("/")
    fun updateTeacher(@Valid @RequestBody teacher: TeacherDTO): TeacherDTO {
        MDC.put("teacherRequest", teacher.id.toString())
        logger.info("/teacher/ updateTeacher")
        return teacherService.updateTeacher(teacher)
    }

    @DeleteMapping("/{id}")
    fun deleteTeacherById(@PathVariable id: Long) {
        MDC.put("teacherRequest", id.toString())
        logger.info("/teacher/$id deleteTeacherById")
        return teacherService.deleteTeacherById(id)
    }
}
