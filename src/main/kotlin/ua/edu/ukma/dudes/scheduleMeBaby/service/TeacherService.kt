package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.repository.TeacherRepository
import java.util.*
import javax.validation.constraints.NotBlank

data class CreateTeacherDTO(@NotBlank val name: String)
typealias UpdateTeacherDTO = CreateTeacherDTO

@Service
class TeacherService(private val teacherRepository: TeacherRepository) {
    fun findAllTeachers(): Iterable<Teacher> = teacherRepository.findAll()

    @Cacheable("teacher")
    fun findTeacherById(id: Long): Optional<Teacher> = teacherRepository.findById(id)

    @CacheEvict("teacher")
    fun deleteTeacherById(id: Long) = teacherRepository.deleteById(id)

    @CacheEvict("teacher")
    fun createTeacher(request: CreateTeacherDTO): TeacherDTO =
        teacherRepository.save(Teacher(name = request.name)).toDto()

    @CacheEvict("teacher")
    fun updateTeacher(id: Long, patch: UpdateTeacherDTO): TeacherDTO {
        val teacher = teacherRepository.findById(id)
            .orElseThrow { NotFoundException("Cannot find teacher by id: $id") }
        teacher.name = patch.name
        return teacherRepository.save(teacher).toDto()
    }
}
