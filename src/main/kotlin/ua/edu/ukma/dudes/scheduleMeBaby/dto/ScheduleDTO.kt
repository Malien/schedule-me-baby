package ua.edu.ukma.dudes.scheduleMeBaby.dto

data class ScheduleDTO(
    val faculty: String,
    val speciality: String,
    val studyYear: Int,
    val years: String
) {
    val days: MutableList<Day> = mutableListOf()

    override fun toString(): String {
        return "ScheduleDTO(faculty=$faculty, speciality=$studyYear, years=$years, days=$days)"
    }
}

data class Day(
    val day: Days
){
    val dayEntries: MutableList<DayEntry> = mutableListOf()

    override fun toString(): String {
        return "Day($day, $dayEntries)"
    }
}

data class DayEntry(
    val time: String,
    val subjectName: String,
    val teacherName: String,
    val group: String,
    val weeks: String,
    val auditorium: String
) {

}

enum class Days {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    companion object {
        fun mapValue(value: String): Days? {
            return if (value.lowercase() == "понеділок") {
                MONDAY
            } else if (value.lowercase() == "вівторок") {
                TUESDAY
            } else if (value.lowercase() == "середа") {
                WEDNESDAY
            } else if (value.lowercase() == "четвер") {
                THURSDAY
            } else if (value.lowercase() == "п`ятниця") {
                FRIDAY
            } else if (value.lowercase() == "субота") {
                SATURDAY
            } else if (value.lowercase() == "неділя") {
                SUNDAY
            } else null
        }
    }
}