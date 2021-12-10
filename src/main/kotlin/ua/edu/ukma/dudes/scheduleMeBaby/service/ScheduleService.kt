package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.apache.commons.lang3.math.NumberUtils.toInt
import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.ScheduleDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.*
import ua.edu.ukma.dudes.scheduleMeBaby.repository.*
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.UserPrincipal
import java.io.*

@Service
class ScheduleService(
    private val fileRepository: FileRepository,
    private val groupRepository: GroupRepository,
    private val subjectRepository: SubjectRepository,
    private val teacherRepository: TeacherRepository,
    private val timeslotRepository: TimeslotRepository,
    private val scheduleXLSXParser: ScheduleXLSXParser
) {

    fun save(scheduleDTO: ScheduleDTO){
        for (day in scheduleDTO.days) {

            for (dayEntry in day.dayEntries) {
                val subject = getSubject(dayEntry.subjectName)
                val teacher = getTeacher(dayEntry.teacherName)

                val number = dayEntry.group.toIntOrNull() ?: 0
                val type = if (number > 0) 1 else 0
                val group = getGroup(subject, teacher, number, type)

                val timeslot = Timeslot(
                    day.day.ordinal + 1,
                    mapTime(dayEntry.time),
                    dayEntry.auditorium,
                    mapWeeksToCommaSep(dayEntry.weeks),
                    group
                )

                timeslotRepository.save(timeslot)
            }
        }
    }


    fun delete(scheduleDTO: ScheduleDTO){
        val groups: MutableList<Group> = mutableListOf()
        val teachers: MutableList<Teacher> = mutableListOf()
        val subjects: MutableList<Subject> = mutableListOf()

        for (day in scheduleDTO.days) {
            for (dayEntry in day.dayEntries) {
                val subject = getSubject(dayEntry.subjectName)
                val teacher = getTeacher(dayEntry.teacherName)

                val number = dayEntry.group.toIntOrNull() ?: 0
                val type = if (number > 0) 1 else 0
                val group = getGroup(subject, teacher, number, type)

                val timeslot = Timeslot(
                    day.day.ordinal,
                    mapTime(dayEntry.time),
                    dayEntry.auditorium,
                    mapWeeksToCommaSep(dayEntry.weeks),
                    group
                )

                timeslotRepository.delete(timeslot)
                groups.add(group)
                teachers.add(teacher)
                subjects.add(subject)
            }
        }
        groupRepository.deleteAll(groups)
        teacherRepository.deleteAll(teachers)
        subjectRepository.deleteAll(subjects)
    }


    fun saveFile(file: InputStream, user: User, fileName: String): Long? {
        val fileToSave = File(File("src\\main\\resources\\static\\schedules\\").absolutePath + "\\$fileName")
        return if (fileToSave.createNewFile()) {
            val fileOutputStream = FileOutputStream(fileToSave)
            fileOutputStream.write(file.readAllBytes())
            fileOutputStream.close()
            fileRepository.save(FileEntity(fileName, user)).fileId
        } else null
    }

    fun findAllFiles(nameFilter: String): List<FileEntity> {
        return fileRepository.findAllByFilenameContainingIgnoreCaseOrderByFilename(nameFilter)
    }


    fun deleteSchedule(id: Long){
        val fileEntity = fileRepository.findById(id).orElseThrow()
        val file = File(File("src\\main\\resources\\static\\schedules\\").absolutePath + "\\${fileEntity.filename}")
        val scheduleDTO = scheduleXLSXParser.readFromExcel(FileInputStream(file))
        delete(scheduleDTO!!)
        file.delete()
        fileRepository.deleteById(id)
    }


    private fun getTeacher(name: String) : Teacher {
        val teachers = teacherRepository.findAllByName(name).toList()
        return if (teachers.isEmpty()){
            val teacher = teacherRepository.save(Teacher(name))
            teacher
        } else {
            teachers[0]
        }
    }


    private fun getSubject(name: String) : Subject {
        val subjects = subjectRepository.findAllByNameContainingIgnoreCaseOrderByName(name).toList()
        return if (subjects.isEmpty()){
            subjectRepository.save(Subject(name))
        } else {
            subjects[0]
        }
    }


    private fun getGroup( subject: Subject,  teacher: Teacher,
                          number: Int,  type: Int): Group {
        val groups = groupRepository.findAllByAllFields(subject, teacher, number, type)
        return if (groups.isEmpty()){
            groupRepository.save(Group(number, type, subject, teacher))
        } else {
            groups[0]
        }
    }


    private fun mapTime(time: String): Int =
        when (time) {
            "8:30-9:50" -> 1
            "08:30-09:50" -> 1
            "10:00-11:20" -> 2
            "11:40-13:00" -> 3
            "13:30-14:50" -> 4
            "15:00-16:20" -> 5
            "16:30-17:50" -> 6
            "18:00-19:20" -> 7
            else -> -1
        }

    private fun mapWeeksToCommaSep(weeks: String): String {
        val list = mutableListOf<Int>()
        for (i in weeks.split(',')){
            if (i.contains('-')){
                val split = i.split('-')
                val start = split[0]
                val end = split[1]
                for (week in toInt(start) .. toInt(end))
                    list.add(week)
            } else list.add(toInt(i))
        }
        return list.joinToString(",")
    }

}