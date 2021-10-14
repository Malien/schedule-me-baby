package ua.edu.ukma.dudes.scheduleMeBaby.dto

import javax.validation.constraints.NotBlank

data class StudentDTO(var id: Long?, @field:NotBlank var name: String?)