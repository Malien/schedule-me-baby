package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
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
    val id: Long,
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

    fun findTimeslotByID(id: Long): Optional<Timeslot> = timeslotRepository.findById(id)

    fun deleteTimeslotByID(id: Long) = timeslotRepository.deleteById(id)

    fun createTimeslot(createDto: CreateTimeslotDTO): Timeslot =
        timeslotRepository.save(
            Timeslot(
                day = createDto.day,
                clazz = createDto.clazz,
                auditorium = createDto.auditorium,
                weeks = createDto.weeks,
                group = groupRepository.findByIdOrNull(createDto.groupId)
                    ?: throw RuntimeException("Cannot find group with id ${createDto.groupId}")
            )
        )
}