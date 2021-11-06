package ua.edu.ukma.dudes.scheduleMeBaby.dto

import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.media.Schema
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group

data class GroupDTO(val id: Long, val number: Int, val type: Int)

fun Group.toDto(): GroupDTO = GroupDTO(groupId!!, number, type)
