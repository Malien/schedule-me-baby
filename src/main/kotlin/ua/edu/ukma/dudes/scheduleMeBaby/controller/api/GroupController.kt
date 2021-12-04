package ua.edu.ukma.dudes.scheduleMeBaby.controller.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.slf4j.MarkerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.CreateGroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.GroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.UpdateGroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.GroupService

private val CONFIDENTIAL_MARKER = MarkerFactory.getMarker("CONFIDENTIAL")

@RestController
@RequestMapping("/group")
@Tag(name = "Group", description = "Group related operations")
class GroupController(private val groupService: GroupService) {
    private val logger = LoggerFactory.getLogger(StudentController::class.java)

    // TODO: filtering via query params
    @Operation(summary = "Retrieve all groups")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200", description = "All groups", content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = GroupDTO::class))
            )]
        )]
    )
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'USER')")
    fun getGroups(): Iterable<GroupDTO> {
        logger.info(CONFIDENTIAL_MARKER, "/groups/ getGroups")
        return groupService.findAllGroups().map(Group::toDto)
    }

    @Operation(summary = "Retrieve group by it's id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Found Groups", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = GroupDTO::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "Not found", content = [Content()])
        ]
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    fun getGroupsById(@PathVariable id: Long): GroupDTO {
        MDC.put("item_id", id.toString())
        logger.info("/group/$id getGroupById")
        return groupService
            .findGroupById(id)
            .orElseThrow { NotFoundException("Group not found with id: $id") }
            .toDto()
    }

    @Operation(summary = "Create new group")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Created group", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = GroupDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Teacher/Subject by id cannot be found",
                content = [Content()]
            ),
        ]
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = CreateGroupDTO::class)
        )]
    )
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    fun createGroup(@RequestBody group: CreateGroupDTO): GroupDTO {
        val group = groupService.createGroup(group).toDto()
        MDC.put("groupRequest", group.id.toString())
        logger.info("PUT /group/ createGroup")
        return group
    }

    @Operation(summary = "Update existing group")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Updated group", content = [Content()]),
            ApiResponse(
                responseCode = "404",
                description = "Subject/Teacher/Group by id cannot be found",
                content = [Content()]
            ),
        ]
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = UpdateGroupDTO::class)
        )]
    )
    @PatchMapping("/{id}")
    @PreAuthorize("ADMIN")
    fun updateGroup(@PathVariable id: Long, @RequestBody group: UpdateGroupDTO) {
        MDC.put("item_id", id.toString())
        logger.info("PATCH /group/$id updateGroup")
        return groupService.updateGroup(id, group)
    }

    @Operation(summary = "Delete existing group")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Deleted group", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Group by id cannot be found", content = [Content()]),
        ]
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteGroupById(@PathVariable id: Long) {
        MDC.put("item_id", id.toString())
        logger.info("DELETE /group/$id deleteGroupBuId")
        groupService.deleteGroupById(id)
    }
}