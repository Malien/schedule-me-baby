package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TimeslotDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot
import ua.edu.ukma.dudes.scheduleMeBaby.repository.GroupRepository
import ua.edu.ukma.dudes.scheduleMeBaby.repository.TimeslotRepository
import java.util.*

data class CreateTimeslotDTO(
    val day: Int,
    val clazz: Int,
    val auditorium: String,
    val weeks: String,
    val groupId: Long
)

data class UpdateTimeslotDTO(
    val day: Int? = null,
    val clazz: Int? = null,
    val auditorium: String? = null,
    val weeks: String? = null
)

@Service
class TimeslotService(
    private val timeslotRepository: TimeslotRepository,
    private val groupRepository: GroupRepository
) {
    fun findAllTimeslots(): Iterable<Timeslot> = timeslotRepository.findAll()

    fun findTimeslotById(id: Long): Optional<Timeslot> = timeslotRepository.findById(id)

    fun deleteTimeslotById(id: Long) = timeslotRepository.deleteById(id)

    fun createTimeslot(createDto: TimeslotDTO): Timeslot =
        timeslotRepository.save(
            Timeslot(
                day = createDto.day,
                clazz = createDto.clazz,
                auditorium = createDto.auditorium,
                weeks = createDto.weeks,
                group = groupRepository.findByIdOrNull(createDto.groupId ?: -1)
                    ?: throw RuntimeException("Cannot find group with id ${createDto.groupId}")
            )
        )

    fun updateTimeslot(id: Long, updateDto: UpdateTimeslotDTO) {
        val (day, clazz, auditorium, weeks) = updateDto
        val timeslot = timeslotRepository.findByIdOrNull(id)
            ?: throw RuntimeException("Cannot find timeslot with id $id")
        if (day != null) {
            timeslot.day = day
        }
        if (clazz != null) {
            timeslot.clazz = clazz
        }
        if (auditorium != null) {
            timeslot.auditorium = auditorium
        }
        if (weeks != null) {
            timeslot.weeks = weeks
        }
        timeslotRepository.save(timeslot)
    }
}