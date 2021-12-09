package ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserCredentials(
    @field:NotBlank(message = "Login should be valid")
    val login: String,
    @field:Size(min = 8, max = 100, message = "Password length must be between 8 and 100")
    val password: String
)