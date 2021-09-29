package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.SubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Subject
import ua.edu.ukma.dudes.scheduleMeBaby.repository.SubjectRepository
import java.util.*

@Service
class SubjectService() {
    // TODO just for example, move to ctor
    @Autowired
    private lateinit var subjectRepository: SubjectRepository;

    fun findSubjectById(id: Long): Optional<SubjectDTO> = subjectRepository.findById(id).map { mapToDTO(it) }

    fun findAllSubjects(): Iterable<SubjectDTO> = subjectRepository.findAll().map { mapToDTO(it) }

    fun saveSubject(subjectDTO: SubjectDTO): SubjectDTO {
        val newSubject = Subject(subjectDTO.id, subjectDTO.name)
        return mapToDTO(subjectRepository.save(newSubject))
    }

    fun deleteSubjectById(id: Long) {
        subjectRepository.deleteById(id)
    }

    fun mapToDTO(subject: Subject) : SubjectDTO = SubjectDTO(subject.subject_id, subject.name)
}