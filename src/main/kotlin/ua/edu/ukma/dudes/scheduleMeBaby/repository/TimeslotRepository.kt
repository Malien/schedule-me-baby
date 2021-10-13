package ua.edu.ukma.dudes.schedulemebaby.repository

import org.springframework.data.repository.CrudRepository
import ua.edu.ukma.dudes.schedulemebaby.entity.Timeslot

interface TimeslotRepository: CrudRepository<Timeslot, Long>
