package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.CreateTeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.UpdateTeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.repository.TeacherRepository
import java.util.*

@Service
class TeacherService(private val teacherRepository: TeacherRepository) {
    fun findAllTeachers(): Iterable<TeacherDTO> = teacherRepository.findAll().map(Teacher::toDto)

    fun findTeacherById(id: Long): Optional<TeacherDTO> = teacherRepository.findById(id).map(Teacher::toDto)

    fun deleteTeacherById(id: Long) = teacherRepository.deleteById(id)

    fun findTeacherByName(name: String): Optional<TeacherDTO> {
        val teachers = teacherRepository.findByName(name).toList()
        assert(teachers.isEmpty() || teachers.size == 1)
        return if (teachers.isEmpty()) {
            Optional.empty<TeacherDTO>()
        } else {
            Optional.of(teachers[0].toDto())
        }
    }

    fun createTeacher(request: CreateTeacherDTO): TeacherDTO =
        teacherRepository.save(Teacher(name = request.name)).toDto()

    fun updateTeacher(id: Long, patch: UpdateTeacherDTO): TeacherDTO {
        val teacher = teacherRepository.findById(id)
            .orElseThrow { NotFoundException("Cannot find teacher by id: $id") }
        teacher.name = patch.name
        return teacherRepository.save(teacher).toDto()
    }
}
