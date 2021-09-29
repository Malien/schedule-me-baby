package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Student
import ua.edu.ukma.dudes.scheduleMeBaby.repository.StudentRepository
import java.util.*

@Service
class StudentService(private val studentRepository: StudentRepository) {
    fun findAllStudents(): MutableIterable<Student> = studentRepository.findAll()

    fun findStudentByID(id: Int): Optional<Student> = studentRepository.findById(id)

    fun deleteStudentByID(id: Int) = studentRepository.deleteById(id)

    fun saveStudent(student: Student): Student = studentRepository.save(student)
}