package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import ua.edu.ukma.dudes.scheduleMeBaby.exception.InvalidArgumentException
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.repository.TeacherRepository
import java.util.*

@Service
class TeacherService(private val teacherRepository: TeacherRepository) {
    fun findAllTeachers(): Iterable<Teacher> = teacherRepository.findAll()

    fun findTeacherByID(id: Long): Optional<Teacher> = teacherRepository.findById(id)

    fun deleteTeacherByID(id: Long) = teacherRepository.deleteById(id)

    fun createTeacher(teacherDTO: TeacherDTO): TeacherDTO {
        if (teacherDTO.name.isNullOrBlank())
            throw InvalidArgumentException()
        val teacher = Teacher(teacherDTO.name!!)
        return mapToDTO(teacherRepository.save(teacher))
    }

    fun updateTeacher(studentDTO: TeacherDTO): TeacherDTO {
        if (studentDTO.id == null)
            throw NotFoundException()
        if (studentDTO.name.isNullOrBlank())
            throw InvalidArgumentException()
        val teacher = Teacher(studentDTO.name!!)
        teacher.teacherId = studentDTO.id
        return mapToDTO(teacherRepository.save(teacher))
    }

    fun mapToDTO(teacher: Teacher): TeacherDTO = TeacherDTO(teacher.teacherId, teacher.name)
}
