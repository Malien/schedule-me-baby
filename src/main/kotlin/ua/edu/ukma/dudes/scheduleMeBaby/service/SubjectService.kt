package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.CreateSubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.SubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.UpdateSubjectDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Subject
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.repository.SubjectRepository
import java.util.*

@Service
class SubjectService(val subjectRepository: SubjectRepository) {
    fun findSubjectById(id: Long): Optional<SubjectDTO> = subjectRepository.findById(id).map(Subject::toDto)

    fun findAllSubjects(): Iterable<SubjectDTO> = subjectRepository.findAll().map(Subject::toDto)

    fun createSubject(request: CreateSubjectDTO): SubjectDTO =
        subjectRepository.save(Subject(name = request.name)).toDto()

    fun updateSubject(id: Long, patch: UpdateSubjectDTO): SubjectDTO {
        val subject = subjectRepository.findById(id)
            .orElseThrow { NotFoundException("Cannot find subject by id: $id") }
        subject.name = patch.name
        return subjectRepository.save(subject).toDto()
    }

    fun deleteSubjectById(id: Long) {
        subjectRepository.deleteById(id)
    }

}