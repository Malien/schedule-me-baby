package ua.edu.ukma.dudes.scheduleMeBaby.dto

import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group

data class GroupDTO(val id: Long, val number: Int, val type: Int)

fun Group.toDto(): GroupDTO = GroupDTO(groupId!!, number, type)

data class CreateGroupDTO(val number: Int, val type: Int, val subjectId: Long, val teacherId: Long)

data class UpdateGroupDTO(val teacherId: Long)
