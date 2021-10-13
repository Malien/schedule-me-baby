package ua.edu.ukma.dudes.schedulemebaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.schedulemebaby.entity.Timeslot
import ua.edu.ukma.dudes.schedulemebaby.repository.TimeslotRepository
import java.util.*

@Service
class TimeslotService(private val timeslotRepository: TimeslotRepository) {
    fun findAllTimeslots(): Iterable<Timeslot> = timeslotRepository.findAll()

    fun findTimeslotByID(id: Long): Optional<Timeslot> = timeslotRepository.findById(id)

    fun deleteTimeslotByID(id: Long) = timeslotRepository.deleteById(id)

    fun saveTimeslot(timeslot: Timeslot): Timeslot = timeslotRepository.save(timeslot)
}