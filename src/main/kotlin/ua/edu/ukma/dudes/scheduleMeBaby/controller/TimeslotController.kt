package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC
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
    private val logger: Logger = LogManager.getLogger()


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
    fun updateTimeslot(@PathVariable id: Long, @RequestBody timeslot: UpdateTimeslotDTO) {
        MDC.put("item_id", id.toString())
        logger.info("UPDATE /timeslot/$id updateTimeslot")
        timeslotService.updateTimeslot(id, timeslot)
    }

    @DeleteMapping("/{id}")
    fun deleteTimeslotById(@PathVariable id: Long) {
        MDC.put("item_id", id.toString())
        logger.info("DELETE /timeslot/$id deleteTimeslotById")
        timeslotService.deleteTimeslotById(id)
    }

}