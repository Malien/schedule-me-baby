package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.StudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Student
import ua.edu.ukma.dudes.scheduleMeBaby.exception.InvalidArgumentException
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.repository.StudentRepository
import java.util.*

@Service
class StudentService(private val studentRepository: StudentRepository) {
    fun findAllStudents(): Iterable<StudentDTO> = studentRepository.findAll().map { mapToDTO(it) }

    fun findStudentById(id: Long): Optional<StudentDTO> = studentRepository.findById(id).map { mapToDTO(it) }

    fun deleteStudentById(id: Long) = studentRepository.deleteById(id)

    fun createStudent(studentDTO: StudentDTO): StudentDTO {
        if (studentDTO.name.isNullOrBlank())
            throw InvalidArgumentException("Student's name cannot be blank")
        val student = Student(studentDTO.name!!)
        return mapToDTO(studentRepository.save(student))
    }

    fun updateStudent(studentDTO: StudentDTO): StudentDTO {
        if (studentDTO.id == null)
            throw NotFoundException("Subject's id cannot be null")
        if (studentDTO.name.isNullOrBlank())
            throw InvalidArgumentException("Student's name cannot be blank")
        val student = Student(studentDTO.name!!)
        student.studentId = studentDTO.id
        return mapToDTO(studentRepository.save(student))
    }

    fun mapToDTO(student: Student): StudentDTO = StudentDTO(student.studentId, student.name)
}