package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TimeslotDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.CreateTimeslotDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.TimeslotService
import ua.edu.ukma.dudes.scheduleMeBaby.service.UpdateTimeslotDTO

@RestController
@RequestMapping("/timeslot")
class TimeslotController(private val timeslotService: TimeslotService) {

    // TODO: Filtering via query params
    @GetMapping("/")
    fun get(): Iterable<TimeslotDTO> = timeslotService.findAllTimeslots().map(Timeslot::toDto)

    @GetMapping("/{id}")
    fun getTimeslotById(@PathVariable id: Long): TimeslotDTO {
        val optional = timeslotService.findTimeslotById(id)
        return if (optional.isPresent)
            optional.get().toDto()
        else
            throw NotFoundException("Timeslot not found with id: $id")
    }

    @PutMapping("/")
    fun createTimeslot(@RequestBody timeslot: CreateTimeslotDTO): TimeslotDTO =
        timeslotService.createTimeslot(timeslot).toDto()

    @PatchMapping("/{id}")
    fun updateTimeslot(@PathVariable id: Long, @RequestBody timeslot: UpdateTimeslotDTO) =
        timeslotService.updateTimeslot(id, timeslot)

    @DeleteMapping("/{id}")
    fun deleteTimeslotById(@PathVariable id: Long) = timeslotService.deleteTimeslotById(id)

}