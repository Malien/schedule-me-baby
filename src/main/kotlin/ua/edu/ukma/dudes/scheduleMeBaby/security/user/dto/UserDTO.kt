package ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto

import ua.edu.ukma.dudes.scheduleMeBaby.entity.Role

class UserDTO(
    val userId: Long,
    val login: String,
    val password: String,
    val roles: Set<Role>
)

class AuthUserDTO(
    val jwtToken: String,
    val userDTO: UserDTO
)