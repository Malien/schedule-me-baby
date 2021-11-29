package ua.edu.ukma.dudes.scheduleMeBaby.dto

import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class TimeslotDTO(
    val id: Long,
    @field:Min(1)
    @field:Max(7)
    val day: Int,
    @field:Min(1)
    @field:Max(8)
    val clazz: Int,
    @field:NotBlank
    val auditorium: String,
    @field:NotNull
//    TODO? @Pattern
    val weeks: String,
    val groupId: Long?,
)

fun Timeslot.toDto() = TimeslotDTO(timeslotId!!, day, clazz, auditorium, weeks, group.groupId)
