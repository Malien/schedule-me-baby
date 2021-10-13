package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import ua.edu.ukma.dudes.scheduleMeBaby.repository.TeacherRepository
import java.util.*

@Service
class TeacherService(private val teacherRepository: TeacherRepository) {
    fun findAllTeachers(): Iterable<Teacher> = teacherRepository.findAll()

    fun findTeacherByID(id: Long): Optional<Teacher> = teacherRepository.findById(id)

    fun deleteTeacherByID(id: Long) = teacherRepository.deleteById(id)

    fun saveTeacher(teacher: Teacher): Teacher = teacherRepository.save(teacher)
}
