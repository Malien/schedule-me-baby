package ua.edu.ukma.dudes.scheduleMeBaby.repository

import org.springframework.data.repository.CrudRepository
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Role

interface RoleRepository : CrudRepository<Role, Long>
