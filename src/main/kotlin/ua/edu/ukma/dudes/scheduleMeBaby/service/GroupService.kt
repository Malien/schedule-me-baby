package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.repository.GroupRepository
import ua.edu.ukma.dudes.scheduleMeBaby.repository.SubjectRepository
import ua.edu.ukma.dudes.scheduleMeBaby.repository.TeacherRepository
import java.util.*

data class CreateGroupDTO(val number: Int, val type: Int, val subjectId: Long, val teacherId: Long)
data class UpdateGroupDTO(val teacherId: Long)

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val subjectRepository: SubjectRepository,
    private val teacherRepository: TeacherRepository
) {
    fun findAllGroups(): Iterable<Group> = groupRepository.findAll()

    fun findGroupById(id: Long): Optional<Group> = groupRepository.findById(id)

    fun deleteGroupById(id: Long) = groupRepository.deleteById(id)

    fun createGroup(createDto: CreateGroupDTO): Group =
        // There are definitely better exception types to be thrown, rather then RuntimeException
        groupRepository.save(
            Group(
                number = createDto.number,
                type = createDto.type,
                subject = subjectRepository.findByIdOrNull(createDto.subjectId)
                    ?: throw NotFoundException("Teacher by id ${createDto.teacherId} is not present "),
                teacher = teacherRepository.findByIdOrNull(createDto.teacherId)
                    ?: throw NotFoundException("Teacher by id ${createDto.teacherId} is not present")
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
