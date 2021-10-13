package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.GroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group
import ua.edu.ukma.dudes.scheduleMeBaby.service.CreateGroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.GroupService
import ua.edu.ukma.dudes.scheduleMeBaby.service.UpdateGroupDTO

@RestController
@RequestMapping("/group")
class GroupController(private val groupService: GroupService) {

    // TODO: filtering via query params
    @GetMapping("/")
    fun getGroups(): ResponseEntity<Iterable<GroupDTO>> =
        ResponseEntity.ok(groupService.findAllGroups().map(Group::toDto))

    @GetMapping("/{id}")
    fun getGroupsByID(@PathVariable id: Long): ResponseEntity<GroupDTO> {
        val optional = groupService.findGroupByID(id)
        return if (optional.isPresent)
            ResponseEntity.ok(optional.get().toDto())
        else
            ResponseEntity.notFound().build()
    }

    @PutMapping("/")
    fun createGroup(@RequestBody group: CreateGroupDTO): GroupDTO =
        groupService.createGroup(group).toDto()

    @PatchMapping("/{id}")
    fun updateGroup(@PathVariable id: Long, @RequestBody group: UpdateGroupDTO) =
        groupService.updateGroup(group)

    @DeleteMapping("/{id}")
    fun deleteGroupById(@PathVariable id: Long) = groupService.deleteGroupByID(id)
}