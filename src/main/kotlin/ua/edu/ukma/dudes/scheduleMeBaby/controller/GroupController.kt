package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.slf4j.MarkerFactory
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.GroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.CreateGroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.GroupService
import ua.edu.ukma.dudes.scheduleMeBaby.service.UpdateGroupDTO

private val CONFIDENTIAL_MARKER = MarkerFactory.getMarker("CONFIDENTIAL")

@RestController
@RequestMapping("/group")
class GroupController(private val groupService: GroupService) {
    private val logger = LoggerFactory.getLogger(StudentController::class.java)

    // TODO: filtering via query params
    @GetMapping("/")
    fun getGroups(): Iterable<GroupDTO> {
        logger.info(CONFIDENTIAL_MARKER, "/groups/ getGroups")
        return groupService.findAllGroups().map(Group::toDto)
    }

        @GetMapping("/{id}")
        fun getGroupsById(@PathVariable id: Long): GroupDTO {
            MDC.put("groupRequest", id.toString())
            logger.info("/group/$id getGroupById")
            val optional = groupService.findGroupById(id)
            return if (optional.isPresent)
                optional.get().toDto()
            else
                throw NotFoundException("Group not found with id: $id")
        }

        @PutMapping("/")
        fun createGroup(@RequestBody group: CreateGroupDTO): GroupDTO {
            logger.info("PUT /group/ createGroup")
            return groupService.createGroup(group).toDto()
        }

        @PatchMapping("/{id}")
        fun updateGroup(@PathVariable id: Long, @RequestBody group: UpdateGroupDTO) {
            MDC.put("groupRequest", id.toString())
            logger.info("PATCH /group/$id updateGroup")
            return groupService.updateGroup(id, group)
        }

        @DeleteMapping("/{id}")
        fun deleteGroupById(@PathVariable id: Long) {
            MDC.put("groupRequest", id.toString())
            logger.info("DELETE /group/$id deleteGroupBuId")
            groupService.deleteGroupById(id)
        }
    }
}