package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.CreateGroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.UpdateGroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.repository.GroupRepository
import ua.edu.ukma.dudes.scheduleMeBaby.repository.SubjectRepository
import ua.edu.ukma.dudes.scheduleMeBaby.repository.TeacherRepository
import java.util.*

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val subjectRepository: SubjectRepository,
    private val teacherRepository: TeacherRepository
) {
    fun findAllGroups(): Iterable<Group> = groupRepository.findAll()

    fun findAllGroupsForSubject(subjectId: Long) = groupRepository.findAllBySubjectId(subjectId)

    fun findGroupById(id: Long): Optional<Group> = groupRepository.findById(id)

    fun deleteGroupById(id: Long) = groupRepository.deleteById(id)

    fun createGroup(createDto: CreateGroupDTO): Group =
        // There are definitely better exception types to be thrown, rather then RuntimeException
        groupRepository.save(
            Group(
                number = createDto.number,
                type = createDto.type,
                subject = subjectRepository.findByIdOrNull(createDto.subjectId)
                    ?: throw NotFoundException("Subject with id ${createDto.subjectId} is not present "),
                teacher = teacherRepository.findByIdOrNull(createDto.teacherId)
                    ?: throw NotFoundException("Teacher with id ${createDto.teacherId} is not present")
            )
        )

    fun updateGroup(id: Long, updateDto: UpdateGroupDTO) {
        val (teacherId) = updateDto
        val group = groupRepository.findByIdOrNull(id)
            ?: throw NotFoundException("Cannot find group with id $id")
        group.teacher = teacherRepository.findByIdOrNull(teacherId)
            ?: throw NotFoundException("Cannot find teacher with id $teacherId")
        groupRepository.save(group)
    }

}
