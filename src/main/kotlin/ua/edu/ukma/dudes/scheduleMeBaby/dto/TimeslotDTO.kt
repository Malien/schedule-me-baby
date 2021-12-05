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

data class TimeslotUIDTO(
    val id: Long,
    val groupId: Long,
    val day: String,
    val clazz: Int,
    val auditorium: String,
    val weeks: String,
)

fun Timeslot.toUIDto() = TimeslotUIDTO(timeslotId!!, group.groupId!!, dayIntToString(day), clazz, auditorium, weeks.replace(",", " "))

data class CreateTimeslotFormDTO(
    val groupId: Long,
    val day: String,
    val clazz: Int,
    val auditorium: String,
    val weeks: List<Int>,
)

data class CreateTimeslotDTO(
    val groupId: Long,
    val day: Int,
    val clazz: Int,
    val auditorium: String,
    val weeks: String,
)

data class UpdateTimeslotDTO(
    val day: Int? = null,
    val clazz: Int? = null,
    val auditorium: String? = null,
    val weeks: String? = null
)

fun dayStringToInt(day: String) : Int {
    return when (day) {
        "Monday" -> 1
        "Tuesday" -> 2
        "Wednesday" -> 3
        "Thursday" -> 4
        "Friday" -> 5
        "Saturday" -> 6
        "Sunday" -> {
            assert(false) // not reached
            7
        }
        else -> -1
    }
}

fun dayIntToString(day: Int) : String {
    return when (day) {
        1 -> "Monday"
        2 -> "Tuesday"
        3 -> "Wednesday"
        4 -> "Thursday"
        5 -> "Friday"
        6 -> "Saturday"
        7 -> {
            assert(false) // not reached
            return "Sunday"
        }
        else -> "Unknown"
    }
}
