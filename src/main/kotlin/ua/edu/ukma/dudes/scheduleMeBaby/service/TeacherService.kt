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

    fun findTeacherById(id: Long): Optional<Teacher> = teacherRepository.findById(id)

    fun deleteTeacherById(id: Long) = teacherRepository.deleteById(id)

    fun createTeacher(teacherDTO: TeacherDTO): TeacherDTO {
        if (teacherDTO.name.isNullOrBlank())
            throw InvalidArgumentException("Teacher's name cannot be blank")
        val teacher = Teacher(teacherDTO.name!!)
        return mapToDTO(teacherRepository.save(teacher))
    }

    fun updateTeacher(studentDTO: TeacherDTO): TeacherDTO {
        if (studentDTO.id == null)
            throw NotFoundException("Teacher's id cannot be null")
        if (studentDTO.name.isNullOrBlank())
            throw InvalidArgumentException("Teacher's name cannot be blank")
        val teacher = Teacher(studentDTO.name!!)
        teacher.teacherId = studentDTO.id
        return mapToDTO(teacherRepository.save(teacher))
    }

    fun mapToDTO(teacher: Teacher): TeacherDTO = TeacherDTO(teacher.teacherId, teacher.name)
}
