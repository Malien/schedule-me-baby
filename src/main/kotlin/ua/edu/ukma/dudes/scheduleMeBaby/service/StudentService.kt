package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.CreateStudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.StudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.UpdateStudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Student
import ua.edu.ukma.dudes.scheduleMeBaby.exception.InvalidArgumentException
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.repository.GroupRepository
import ua.edu.ukma.dudes.scheduleMeBaby.repository.StudentRepository
import java.util.*

@Service
class StudentService(
    private val studentRepository: StudentRepository,
    private val groupRepository: GroupRepository,
) {
    fun findAllStudents(): Iterable<StudentDTO> = studentRepository.findAll().map(Student::toDto)

    fun findStudentById(id: Long): Optional<StudentDTO> = studentRepository.findById(id).map(Student::toDto)

    fun deleteStudentById(id: Long) = studentRepository.deleteById(id)

    fun createStudent(request: CreateStudentDTO): StudentDTO =
        studentRepository.save(Student(request.name)).toDto()

    fun updateStudent(id: Long, dto: UpdateStudentDTO): StudentDTO {
        val student = studentRepository.findById(id)
            .orElseThrow { NotFoundException("Cannot find student by id: $id") }
        if (dto.name[0].isLowerCase())
            throw InvalidArgumentException("Student's name should not be lower case: " + dto.name)
        student.name = dto.name
        return studentRepository.save(student).toDto()
    }

    fun enrollGroup(studentId: Long, groupId: Long, enroll: Boolean): Boolean {
        val student = studentRepository.findById(studentId).orElseThrow {
            NotFoundException("Student with id $studentId does not exist")
        }
        val group = groupRepository.findById(groupId).orElseThrow {
            NotFoundException("Group with id $groupId does not exist")
        }
        val result = if (enroll)
            student.studentGroups.add(group)
        else student.studentGroups.remove(group)
        studentRepository.save(student)
        return result
    }

}