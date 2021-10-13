package ua.edu.ukma.dudes.scheduleMeBaby.dto

import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot

data class TimeslotDTO(
    val id: Long,
    val day: Int,
    val clazz: Int,
    val auditorium: String,
    val weeks: String,
)

fun Timeslot.toDto() = TimeslotDTO(timeslotId!!, day, clazz, auditorium, weeks)
