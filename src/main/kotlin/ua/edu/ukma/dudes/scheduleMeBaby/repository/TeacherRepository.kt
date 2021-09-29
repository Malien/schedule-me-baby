package ua.edu.ukma.dudes.schedulemebaby.repository

import org.springframework.data.repository.CrudRepository
import ua.edu.ukma.dudes.schedulemebaby.entity.Teacher

interface TeacherRepository : CrudRepository<Teacher, Int>
