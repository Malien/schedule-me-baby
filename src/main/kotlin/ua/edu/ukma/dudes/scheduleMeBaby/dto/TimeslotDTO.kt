package ua.edu.ukma.dudes.scheduleMeBaby.dto

import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class TimeslotDTO(
    val id: Long,
    @Min(1)
    @Max(7)
    val day: Int,
    @Min(1)
    @Max(8)
    val clazz: Int,
    @NotBlank
    val auditorium: String,
    @NotNull
//    TODO? @Pattern
    val weeks: String,
)

fun Timeslot.toDto() = TimeslotDTO(timeslotId!!, day, clazz, auditorium, weeks)
