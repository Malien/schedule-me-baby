package ua.edu.ukma.dudes.scheduleMeBaby.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot

interface ScheduleItem {
    val day: Int
    val `class`: Int
    val auditorium: String
    val weeks: String
    val groupNumber: Int
    val groupType: Int
    val subjectName: String
    val teacherName: String
}

interface TimeslotRepository : CrudRepository<Timeslot, Long> {
    @Query(value = "SELECT * FROM timeslots t WHERE t.group_id = :groupId", nativeQuery = true)
    fun findAllByGroupId(@Param("groupId") groupId: Long): Iterable<Timeslot>

    @Query(
        value = """
        SELECT 
            ts.day, ts.class, ts.auditorium, ts.weeks,
            g.number as "groupNumber", 
            g.type as "groupType",
            s.name as "subjectName",
            t.name as "teacherName"
        FROM timeslots ts
        JOIN groups g ON ts.group_id = g.group_id
        JOIN subjects s ON s.subject_id = g.subject_id
        JOIN teachers t ON t.teacher_id = g.teacher_id
        JOIN students_groups sg ON sg.group_id = g.group_id
        WHERE sg.student_id = :studentId
    """, nativeQuery = true
    )
    fun scheduleForStudent(studentId: Long): List<ScheduleItem>
}

