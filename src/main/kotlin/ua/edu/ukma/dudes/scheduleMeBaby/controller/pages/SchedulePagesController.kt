package ua.edu.ukma.dudes.scheduleMeBaby.controller.pages

import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import ua.edu.ukma.dudes.scheduleMeBaby.controller.user
import ua.edu.ukma.dudes.scheduleMeBaby.repository.ScheduleItem
import ua.edu.ukma.dudes.scheduleMeBaby.service.TimeslotService
import java.security.Principal

val timeslotTimes = listOf("8:30", "10:00", "11:40", "13:30", "15:00", "16:30", "18:00")
private val logger = LoggerFactory.getLogger(SchedulePagesController::class.java)

@Controller
@Tag(name = "Schedule", description = "Schedule specific APIs")
class SchedulePagesController(private val timeslotService: TimeslotService) {

    @GetMapping("/schedule")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    fun schedule(principal: Principal, model: Model): String {
        val user = principal.user
        // Time of day -> Day of the week (except sunday) -> timeslots per cell (can have collisions)
        val schedule = Array(8) {
            Array(6) { ArrayList<ScheduleViewItem>() }
        }.zip(timeslotTimes)

        for (item in timeslotService.scheduleForStudent(user.id!!).map(ScheduleItem::toViewItem)) {
            if ((1..8).contains(item.`class`) && (1..6).contains(item.day)) {
                schedule[item.`class` - 1].first[item.day - 1].add(item)
            } else {
                logger.error("Schedule item contains value out of range: $item")
            }
        }

        model["schedule"] = schedule

        return "schedule"
    }
}

data class ScheduleViewItem(
    val day: Int,
    val `class`: Int,
    val auditorium: String,
    val weeks: String,
    val groupNumber: Int,
    val groupType: String,
    val subjectName: String,
    val teacherName: String,
)

fun ScheduleItem.toViewItem() = ScheduleViewItem(
    day,
    `class`,
    auditorium,
    compactWeeksRepr(weeks),
    groupNumber,
    if (groupType == 0) "Lecture" else "Practice",
    subjectName,
    teacherName
)

fun compactWeeksRepr(weeks: String): String {
    // weeks = 2,4-7,9-10
    val list = weeks.replace(" ", "").split(',').mapNotNull {
        try {
            it.toInt()
        } catch (e: NumberFormatException) {
            logger.error("Weeks field contains invalid numeric value: $it")
            null
        }
    }
    if (list.isEmpty()) return ""

    val res = ArrayList<String>()
    var start = list.first()
    for ((cur, next) in list.zipWithNext()) {
        if (cur + 1 != next) {
            res.add("$start-$cur")
            start = next
        }
    }

    val end = list.last()
    if (start != end) {
        res.add("$start-$end")
    } else {
        res.add(start.toString())
    }

    return res.joinToString()
}
