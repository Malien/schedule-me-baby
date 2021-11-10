package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.StudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Student
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.repository.StudentRepository
import java.util.*
import javax.validation.constraints.NotBlank

data class CreateStudentDTO(@NotBlank val name: String)
typealias UpdateStudentDTO = CreateStudentDTO

@Service
class StudentService(private val studentRepository: StudentRepository) {
    fun findAllStudents(): Iterable<StudentDTO> = studentRepository.findAll().map(Student::toDto)

    fun findStudentById(id: Long): Optional<StudentDTO> = studentRepository.findById(id).map(Student::toDto)

    fun deleteStudentById(id: Long) = studentRepository.deleteById(id)

    fun createStudent(request: CreateStudentDTO): StudentDTO =
        studentRepository.save(Student(request.name)).toDto()

    fun updateStudent(id: Long, patch: UpdateStudentDTO): StudentDTO {
        val student = studentRepository.findById(id)
            .orElseThrow { NotFoundException("Cannot find student by id: $id") }
        student.name = patch.name
        return studentRepository.save(student).toDto()
    }

}