package ua.edu.ukma.dudes.scheduleMeBaby.repository

import org.springframework.data.repository.CrudRepository
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Subject

interface SubjectRepository : CrudRepository<Subject, Long>