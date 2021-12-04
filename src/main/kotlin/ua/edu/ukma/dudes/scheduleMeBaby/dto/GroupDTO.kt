package ua.edu.ukma.dudes.scheduleMeBaby.dto

import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group

data class GroupDTO(val id: Long, val teacherId: Long, val number: Int, val type: Int)

fun Group.toDto(): GroupDTO = GroupDTO(groupId!!, teacher.teacherId!!, number, type)

data class GroupUIDTO(val id: Long, val teacherName: String, val number: Int, val type: String)

fun Group.toUIDto(): GroupUIDTO = GroupUIDTO(groupId!!, teacher.name, number, if (type == 0) "Lection" else "Practice")

data class CreateGroupFormDTO(val subjectId: Long, val teacherName: String, val number: Int, val type: String)

data class CreateGroupDTO(val subjectId: Long, val teacherId: Long, val number: Int, val type: Int)

data class UpdateGroupDTO(val teacherId: Long)
