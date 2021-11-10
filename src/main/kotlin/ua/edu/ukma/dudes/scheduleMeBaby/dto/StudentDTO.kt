package ua.edu.ukma.dudes.scheduleMeBaby.dto

import ua.edu.ukma.dudes.scheduleMeBaby.entity.Student
import javax.validation.constraints.NotBlank

data class StudentDTO(var id: Long, @field:NotBlank var name: String)

fun Student.toDto() = StudentDTO(studentId!!, name)
