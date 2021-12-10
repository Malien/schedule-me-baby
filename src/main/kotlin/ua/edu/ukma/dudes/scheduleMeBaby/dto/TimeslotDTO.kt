package ua.edu.ukma.dudes.scheduleMeBaby.dto

import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot
import javax.validation.constraints.*

data class TimeslotDTO(
    val id: Long,
    @field:Min(1) @field:Max(7)
    val day: Int,
    @field:Min(1) @field:Max(8)
    val clazz: Int,
    @field:NotBlank
    val auditorium: String,
    @field:NotNull
    val weeks: String,
    val groupId: Long?,
)

fun Timeslot.toDto() = TimeslotDTO(timeslotId!!, day, clazz, auditorium, weeks, group.groupId)

data class TimeslotUIDTO(
    val id: Long,
    val groupId: Long,
    @field:Min(1) @field:Max(7)
    val day: String,
    @field:Min(1) @field:Max(8)
    val clazz: Int,
    @field:NotNull
    val auditorium: String,
    @field:NotNull
    val weeks: String,
)

fun Timeslot.toUIDto() = TimeslotUIDTO(timeslotId!!, group.groupId!!, dayIntToString(day), clazz, auditorium, weeks.replace(",", " "))

data class CreateTimeslotFormDTO(
    val groupId: Long,
    @field:NotEmpty
    val day: String,
    @field:Min(1) @field:Max(8)
    val clazz: Int,
    @field:NotNull
    val auditorium: String,
    @field:NotEmpty
    val weeks: List<Int>,
)

data class CreateTimeslotDTO(
    val groupId: Long,
    @field:Min(1) @field:Max(7)
    val day: Int,
    @field:Min(1) @field:Max(8)
    val clazz: Int,
    @field:NotNull
    val auditorium: String,
    @field:NotNull
    val weeks: String,
)

data class UpdateTimeslotDTO(
    @field:Min(1) @field:Max(7)
    val day: Int? = null,
    @field:Min(1) @field:Max(8)
    val clazz: Int? = null,
    @field:NotNull
    val auditorium: String? = null,
    @field:NotNull
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
