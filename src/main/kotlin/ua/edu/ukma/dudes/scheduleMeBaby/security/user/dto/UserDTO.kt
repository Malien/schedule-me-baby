package ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto

import ua.edu.ukma.dudes.scheduleMeBaby.entity.Role
import ua.edu.ukma.dudes.scheduleMeBaby.entity.User

class UserDTO(
    val userId: Long,
    val login: String,
    val roles: Set<Role>
)

class AuthUserDTO(
    val token: String,
    val userDTO: UserDTO
)

fun User.toDto() = UserDTO(id!!, login, roles)