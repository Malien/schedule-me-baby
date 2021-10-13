package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group
import ua.edu.ukma.dudes.scheduleMeBaby.repository.GroupRepository
import java.util.*

@Service
class GroupService(private val groupRepository: GroupRepository) {
    fun findAllGroups(): Iterable<Group> = groupRepository.findAll()

    fun findGroupByID(id: Long): Optional<Group> = groupRepository.findById(id)

    fun deleteGroupByID(id: Long) = groupRepository.deleteById(id)

    fun saveGroup(group: Group): Group = groupRepository.save(group)
}
