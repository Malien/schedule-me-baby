package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Student
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Subject
import ua.edu.ukma.dudes.scheduleMeBaby.repository.SubjectRepository
import java.util.*

@Service
class SubjectService() {
    @Autowired
    private lateinit var subjectRepository: SubjectRepository;

    fun findSubjectById(id: Long): Optional<Subject> = subjectRepository.findById(id)

    fun findAllSubjects(): Iterable<Subject> = subjectRepository.findAll()



}