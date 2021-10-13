package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.GroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toGroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.CreateGroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.GroupService

@RestController
@RequestMapping("/group")
class GroupController(private val groupService: GroupService) {

    @GetMapping("/")
    fun getGroups(): ResponseEntity<Iterable<GroupDTO>> =
        ResponseEntity.ok(groupService.findAllGroups())

    @GetMapping("/{id}")
    fun getGroupsByID(@PathVariable id: Long): ResponseEntity<GroupDTO> {
        val optional = groupService.findGroupByID(id)
        return if (optional.isPresent)
            ResponseEntity.ok(optional.get())
        else
            ResponseEntity.notFound().build()
    }

    @PutMapping("/")
    fun createGroup(@RequestBody group: CreateGroupDTO): GroupDTO =
        groupService.createGroup(group).toGroupDTO()

    @DeleteMapping("/{id}")
    fun deleteGroupById(@PathVariable id: Long) = groupService.deleteGroupByID(id)
}