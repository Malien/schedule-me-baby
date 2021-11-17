package ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class UserCredentials(
    val login: @NotBlank(message = "Login should be valid") String,
    val password: @Size(min = 8, max = 100, message = "Password length must be between 8 and 100") String
)