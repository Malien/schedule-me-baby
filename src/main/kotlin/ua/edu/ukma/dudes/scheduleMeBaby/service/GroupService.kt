package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.GroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toGroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group
import ua.edu.ukma.dudes.scheduleMeBaby.repository.GroupRepository
import ua.edu.ukma.dudes.scheduleMeBaby.repository.SubjectRepository
import ua.edu.ukma.dudes.scheduleMeBaby.repository.TeacherRepository
import java.util.*

data class CreateGroupDTO(val number: Int, val type: Int, val subjectId: Long, val teacherId: Long)
data class UpdateGroupDTO(val groupId: Long, val teacherId: Long)

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val subjectRepository: SubjectRepository,
    private val teacherRepository: TeacherRepository
) {
    fun findAllGroups(): Iterable<GroupDTO> = groupRepository.findAll().map(Group::toGroupDTO)

    fun findGroupByID(id: Long): Optional<GroupDTO> = groupRepository.findById(id).map(Group::toGroupDTO)

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
        val group = groupRepository.findByIdOrNull(updateDto.groupId)
            ?: throw RuntimeException("Cannot find group with id ${updateDto.groupId}")
        group.teacher = teacherRepository.findByIdOrNull(updateDto.teacherId)
            ?: throw RuntimeException("Cannot find teacher with id ${updateDto.teacherId}")
        groupRepository.save(group)
    }

}
