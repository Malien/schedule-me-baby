package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.GroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.CreateGroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.GroupService
import ua.edu.ukma.dudes.scheduleMeBaby.service.UpdateGroupDTO

@RestController
@RequestMapping("/group")
class GroupController(private val groupService: GroupService) {

    // TODO: filtering via query params
    @GetMapping("/")
    fun getGroups(): Iterable<GroupDTO> = groupService.findAllGroups().map(Group::toDto)

    @GetMapping("/{id}")
    fun getGroupsById(@PathVariable id: Long): GroupDTO {
        val optional = groupService.findGroupById(id)
        return if (optional.isPresent)
            optional.get().toDto()
        else
            throw NotFoundException("Group not found with id: $id")
    }

    @PutMapping("/")
    fun createGroup(@RequestBody group: CreateGroupDTO): GroupDTO = groupService.createGroup(group).toDto()

    @PatchMapping("/{id}")
    fun updateGroup(@PathVariable id: Long, @RequestBody group: UpdateGroupDTO) = groupService.updateGroup(id, group)

    @DeleteMapping("/{id}")
    fun deleteGroupById(@PathVariable id: Long) = groupService.deleteGroupById(id)
}