package ua.edu.ukma.dudes.schedulemebaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.schedulemebaby.entity.Group
import ua.edu.ukma.dudes.schedulemebaby.repository.GroupRepository
import java.util.*

@Service
class GroupService(private val groupRepository: GroupRepository) {
    fun findAllGroups(): Iterable<Group> = groupRepository.findAll()

    fun findGroupByID(id: Long): Optional<Group> = groupRepository.findById(id)

    fun deleteGroupByID(id: Long) = groupRepository.deleteById(id)

    fun saveGroup(group: Group): Group = groupRepository.save(group)
}
