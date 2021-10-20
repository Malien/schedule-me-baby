package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.slf4j.*
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import ua.edu.ukma.dudes.scheduleMeBaby.service.TeacherService
import java.util.*
import javax.validation.Valid

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
        MDC.put("item_id", id.toString())
        logger.info("/teacher/$id getTeacherById")
        return teacherService.findTeacherById(id)
    }

    @PostMapping("/")
    fun createTeacher(@Valid @RequestBody teacher: TeacherDTO): TeacherDTO {
        MDC.put("item_id", teacher.id.toString())
        logger.info("/teacher/ createTeacher")
        return teacherService.createTeacher(teacher)
    }

    @PutMapping("/")
    fun updateTeacher(@Valid @RequestBody teacher: TeacherDTO): TeacherDTO {
        MDC.put("item_id", teacher.id.toString())
        logger.info("/teacher/ updateTeacher")
        return teacherService.updateTeacher(teacher)
    }

    @DeleteMapping("/{id}")
    fun deleteTeacherById(@PathVariable id: Long) {
        MDC.put("item_id", id.toString())
        logger.info("/teacher/$id deleteTeacherById")
        return teacherService.deleteTeacherById(id)
    }
}
