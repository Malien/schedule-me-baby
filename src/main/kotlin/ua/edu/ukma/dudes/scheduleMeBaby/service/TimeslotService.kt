package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot
import ua.edu.ukma.dudes.scheduleMeBaby.repository.TimeslotRepository
import java.util.*

@Service
class TimeslotService(private val timeslotRepository: TimeslotRepository) {
    fun findAllTimeslots(): Iterable<Timeslot> = timeslotRepository.findAll()

    fun findTimeslotByID(id: Long): Optional<Timeslot> = timeslotRepository.findById(id)

    fun deleteTimeslotByID(id: Long) = timeslotRepository.deleteById(id)

    fun saveTimeslot(timeslot: Timeslot): Timeslot = timeslotRepository.save(timeslot)
}