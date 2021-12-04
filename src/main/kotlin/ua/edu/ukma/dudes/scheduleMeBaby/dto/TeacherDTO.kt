package ua.edu.ukma.dudes.scheduleMeBaby.dto

import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import javax.validation.constraints.NotBlank

data class TeacherDTO(var id: Long, @field:NotBlank var name: String)

fun Teacher.toDto() = TeacherDTO(teacherId!!, name)

data class CreateTeacherDTO(@NotBlank val name: String)

typealias UpdateTeacherDTO = CreateTeacherDTO
