package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.dto.CreateTimeslotDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TimeslotDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.UpdateTimeslotDTO
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot
import ua.edu.ukma.dudes.scheduleMeBaby.repository.GroupRepository
import ua.edu.ukma.dudes.scheduleMeBaby.repository.TimeslotRepository
import java.util.*

@Service
class TimeslotService(
    private val timeslotRepository: TimeslotRepository,
    private val groupRepository: GroupRepository
) {
    fun findAllTimeslots(): Iterable<Timeslot> = timeslotRepository.findAll()

    fun findAllTimeslots(groupId: Long): Iterable<Timeslot> = timeslotRepository.findAllByGroupId(groupId)

    fun findTimeslotById(id: Long): Optional<Timeslot> = timeslotRepository.findById(id)

    fun deleteTimeslotById(id: Long) = timeslotRepository.deleteById(id)

    fun createTimeslot(createDto: CreateTimeslotDTO): Timeslot =
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