package ua.edu.ukma.dudes.scheduleMeBaby.repository

import org.springframework.data.repository.CrudRepository
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher

interface TeacherRepository : CrudRepository<Teacher, Long>
