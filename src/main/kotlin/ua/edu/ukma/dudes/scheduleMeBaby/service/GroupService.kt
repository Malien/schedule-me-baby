package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group
import ua.edu.ukma.dudes.scheduleMeBaby.repository.GroupRepository
import ua.edu.ukma.dudes.scheduleMeBaby.repository.SubjectRepository
import ua.edu.ukma.dudes.scheduleMeBaby.repository.TeacherRepository
import java.util.*

data class CreateGroupDTO(val number: Int, val type: Int, val subjectId: Long, val teacherId: Long)
data class UpdateGroupDTO(val id: Long, val teacherId: Long)

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val subjectRepository: SubjectRepository,
    private val teacherRepository: TeacherRepository
) {
    fun findAllGroups(): Iterable<Group> = groupRepository.findAll()

    fun findGroupByID(id: Long): Optional<Group> = groupRepository.findById(id)

    fun deleteGroupByID(id: Long) = groupRepository.deleteById(id)

    fun createGroup(createDto: CreateGroupDTO): Group =
        // There are definitely better exception types to be thrown, rather then RuntimeException
        groupRepository.save(
            Group(
                number = createDto.number,
                type = createDto.type,
                subject = subjectRepository.findByIdOrNull(createDto.subjectId)
                    ?: throw RuntimeException("Teacher by id ${createDto.teacherId} is not present "),
                teacher = teacherRepository.findByIdOrNull(createDto.teacherId)
                    ?: throw RuntimeException("Teacher by id ${createDto.teacherId} is not present")
            )
        )

    fun updateGroup(updateDto: UpdateGroupDTO) {
        val group = groupRepository.findByIdOrNull(updateDto.id)
            ?: throw RuntimeException("Cannot find group with id ${updateDto.id}")
        group.teacher = teacherRepository.findByIdOrNull(updateDto.teacherId)
            ?: throw RuntimeException("Cannot find teacher with id ${updateDto.teacherId}")
        groupRepository.save(group)
    }

}
