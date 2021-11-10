package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import ua.edu.ukma.dudes.scheduleMeBaby.exception.InvalidArgumentException
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.repository.TeacherRepository
import java.util.*
import javax.validation.constraints.NotBlank

data class CreateTeacherDTO(@NotBlank val name: String)
typealias UpdateTeacherDTO = CreateTeacherDTO

@Service
class TeacherService(private val teacherRepository: TeacherRepository) {
    fun findAllTeachers(): Iterable<Teacher> = teacherRepository.findAll()

    fun findTeacherById(id: Long): Optional<Teacher> = teacherRepository.findById(id)

    fun deleteTeacherById(id: Long) = teacherRepository.deleteById(id)

    fun createTeacher(request: CreateTeacherDTO): TeacherDTO =
        teacherRepository.save(Teacher(name = request.name)).toDto()

    fun updateTeacher(id: Long, patch: UpdateTeacherDTO): TeacherDTO {
        val teacher = teacherRepository.findById(id)
            .orElseThrow { NotFoundException("Cannot find teacher by id: $id") }
        teacher.name = patch.name
        return teacherRepository.save(teacher).toDto()
    }
}
