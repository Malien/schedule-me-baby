package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.StudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Student
import ua.edu.ukma.dudes.scheduleMeBaby.repository.StudentRepository
import java.util.*

@Service
class StudentService(private val studentRepository: StudentRepository) {
    fun findAllStudents(): Iterable<StudentDTO> = studentRepository.findAll().map { mapToDTO(it) }

    fun findStudentByID(id: Long): Optional<StudentDTO> = studentRepository.findById(id).map { mapToDTO(it) }

    fun deleteStudentByID(id: Long) = studentRepository.deleteById(id)

    fun saveStudent(studentDTO: StudentDTO): StudentDTO {
        val student = Student(studentDTO.name!!)
        return mapToDTO(studentRepository.save(student))
    }

    fun mapToDTO(student: Student) : StudentDTO = StudentDTO(student.studentId, student.name)

}