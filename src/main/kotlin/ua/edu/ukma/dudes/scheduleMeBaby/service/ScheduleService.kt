package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
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
    private val timeslotRepository: TimeslotRepository
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
                    dayEntry.weeks,
                    group
                )

                timeslotRepository.save(timeslot)
            }
        }
    }


    fun saveFile(file: MultipartFile, user: UserPrincipal, fileName: String): Boolean{

        val fileToSave = File(File("src\\main\\resources\\static\\schedules\\").absolutePath + "\\$fileName")
        return if (fileToSave.createNewFile()) {
            val fileOutputStream = FileOutputStream(fileToSave)
            fileOutputStream.write(file.bytes)
            fileOutputStream.close()
            fileRepository.save(FileEntity(fileName, user.userEntity))
            true
        } else false
    }

    fun findAllFiles(): MutableList<FileEntity> {
        return fileRepository.findAll()
    }


    fun deleteSchedule(id: Long){
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
        val subjects = subjectRepository.findAllByNameContainingIgnoreCase(name).toList()
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
        if (time == "8:30-9:50")
            1
        else if (time == "10:00-11:20")
            2
        else if (time == "11:40-13:00")
            3
        else if (time == "13:30-14:50")
            4
        else if (time == "15:00-16:20")
            5
        else if (time == "16:30-17:50")
            6
        else if (time == "18:00-19:20")
            7
        else -1

}