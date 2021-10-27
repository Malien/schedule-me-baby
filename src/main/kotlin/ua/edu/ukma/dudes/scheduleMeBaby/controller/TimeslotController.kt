package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.slf4j.MarkerFactory
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TimeslotDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.CreateTimeslotDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.TimeslotService
import ua.edu.ukma.dudes.scheduleMeBaby.service.UpdateTimeslotDTO

private val CONFIDENTIAL_MARKER = MarkerFactory.getMarker("CONFIDENTIAL")

@RestController
@RequestMapping("/timeslot")
class TimeslotController(private val timeslotService: TimeslotService) {

    private val logger = LoggerFactory.getLogger(TimeslotController::class.java)

    // TODO: Filtering via query params
    @GetMapping("/")
    fun get(): Iterable<TimeslotDTO> {
        logger.info(CONFIDENTIAL_MARKER, "GET /timeslot findAllTimeslots")
        return timeslotService.findAllTimeslots().map(Timeslot::toDto)
    }

    @GetMapping("/{id}")
    fun getTimeslotById(@PathVariable id: Long): TimeslotDTO {
        MDC.put("timeslotRequest", id.toString())
        logger.info("GET /timeslot/$id findTimeslotById")
        val optional = timeslotService.findTimeslotById(id)
        return if (optional.isPresent)
            optional.get().toDto()
        else
            throw NotFoundException("Timeslot not found with id: $id")
    }

    @PostMapping("/")
    fun createTimeslot(@RequestBody timeslot: CreateTimeslotDTO): TimeslotDTO {
        val timeslot = timeslotService.createTimeslot(timeslot).toDto()
        MDC.put("timeslotRequest", timeslot.id.toString())
        logger.info("PUT /timeslot createTimeslot")
        return timeslot
    }

    @PuthMapping("/{id}")
    fun updateTimeslot(@PathVariable id: Long, @RequestBody timeslot: UpdateTimeslotDTO) {
        MDC.put("timeslotRequest", id.toString())
        logger.info("PATCH /timeslot/$id updateTimeslot")
        return timeslotService.updateTimeslot(id, timeslot)
    }

    @DeleteMapping("/{id}")
    fun deleteTimeslotById(@PathVariable id: Long) {
        MDC.put("timeslotRequest", id.toString())
        logger.info("DELETE /timeslot/$id deleteTimeslot")
        timeslotService.deleteTimeslotById(id)
    }

}