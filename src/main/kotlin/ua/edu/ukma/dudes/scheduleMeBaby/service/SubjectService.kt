package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.SubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Subject
import ua.edu.ukma.dudes.scheduleMeBaby.exception.InvalidArgumentException
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.repository.SubjectRepository
import java.util.*

@Service
class SubjectService(val subjectRepository: SubjectRepository) {
    fun findSubjectById(id: Long): Optional<SubjectDTO> = subjectRepository.findById(id).map { mapToDTO(it) }

    fun findAllSubjects(): Iterable<SubjectDTO> = subjectRepository.findAll().map { mapToDTO(it) }

    fun createSubject(subjectDTO: SubjectDTO): SubjectDTO {
        if (subjectDTO.name.isNullOrBlank())
            throw InvalidArgumentException()
        val newSubject = Subject(subjectDTO.name!!)
        return mapToDTO(subjectRepository.save(newSubject))
    }

    fun updateSubject(subjectDTO: SubjectDTO): SubjectDTO {
        if (subjectDTO.id == null)
            throw NotFoundException()
        if (subjectDTO.name.isNullOrBlank())
            throw InvalidArgumentException()
        val subject = Subject(subjectDTO.name!!)
        subject.subjectId = subjectDTO.id
        return mapToDTO(subjectRepository.save(subject))
    }

    fun deleteSubjectById(id: Long) {
        subjectRepository.deleteById(id)
    }

    fun mapToDTO(subject: Subject): SubjectDTO = SubjectDTO(subject.subjectId, subject.name)
}