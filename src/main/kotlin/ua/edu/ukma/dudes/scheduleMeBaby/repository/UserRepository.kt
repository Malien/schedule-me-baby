package ua.edu.ukma.dudes.scheduleMeBaby.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ua.edu.ukma.dudes.scheduleMeBaby.entity.User

interface UserRepository : JpaRepository<User, Long> {
    @Query("SELECT user FROM User user WHERE user.login = :login")
    fun findByLogin(@Param("login") login: String): User?
}