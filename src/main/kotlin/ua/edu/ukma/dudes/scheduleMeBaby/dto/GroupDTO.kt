package ua.edu.ukma.dudes.scheduleMeBaby.dto

import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class GroupDTO(
    val id: Long,
    val teacherId: Long,
    val number: Int,
    @field:Min(0) @field:Max(1)
    val type: Int
)

fun Group.toDto(): GroupDTO = GroupDTO(groupId!!, teacher.teacherId!!, number, type)

data class GroupUIDTO(
    val id: Long,
    @field:NotBlank
    val teacherName: String,
    val number: Int,
    @field:Min(0) @field:Max(1)
    val type: String
)

fun Group.toUIDto(): GroupUIDTO = GroupUIDTO(groupId!!, teacher.name, number, if (type == 0) "Lection" else "Practice")

data class CreateGroupFormDTO(
    val subjectId: Long,
    val teacherName: String,
    val number: Int,
    @field:Pattern(regexp = "(Lection|Practice)")
    val type: String
)

data class CreateGroupDTO(
    val subjectId: Long,
    val teacherId: Long,
    val number: Int,
    @field:Min(0) @field:Max(1)
    val type: Int
)

data class UpdateGroupDTO(val teacherId: Long)
