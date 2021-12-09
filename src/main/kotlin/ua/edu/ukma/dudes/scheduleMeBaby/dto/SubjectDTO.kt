package ua.edu.ukma.dudes.scheduleMeBaby.dto

import ua.edu.ukma.dudes.scheduleMeBaby.entity.Subject
import javax.validation.constraints.NotBlank

data class SubjectDTO(
    var id: Long,
    @field:NotBlank
    var name: String
)

fun Subject.toDto() = SubjectDTO(subjectId!!, name)

data class CreateSubjectDTO(
    @field:NotBlank val name: String
)

typealias UpdateSubjectDTO = CreateSubjectDTO
