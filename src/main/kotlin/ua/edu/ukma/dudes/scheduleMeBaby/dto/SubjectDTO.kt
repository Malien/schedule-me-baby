package ua.edu.ukma.dudes.scheduleMeBaby.dto

import javax.validation.constraints.NotBlank

data class SubjectDTO(var id: Long?, @field:NotBlank var name: String)