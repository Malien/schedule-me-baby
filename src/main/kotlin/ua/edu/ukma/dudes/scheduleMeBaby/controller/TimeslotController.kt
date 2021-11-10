package ua.edu.ukma.dudes.scheduleMeBaby.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.slf4j.MarkerFactory
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TeacherDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TimeslotDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.CreateTimeslotDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.TimeslotService
import ua.edu.ukma.dudes.scheduleMeBaby.service.UpdateTimeslotDTO
import javax.validation.Valid

private val CONFIDENTIAL_MARKER = MarkerFactory.getMarker("CONFIDENTIAL")

@RestController
@RequestMapping("/timeslot")
@Tag(name = "Timeslot", description = "Timeslot related APIs")
class TimeslotController(private val timeslotService: TimeslotService) {

    private val logger = LoggerFactory.getLogger(TimeslotController::class.java)

    // TODO: Filtering via query params
    @Operation(summary = "Retrieve all timeslots")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200", description = "All timeslots", content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = TimeslotDTO::class))
            )]
        )]
    )
    @GetMapping("/")
    fun get(): Iterable<TimeslotDTO> {
        logger.info(CONFIDENTIAL_MARKER, "GET /timeslot findAllTimeslots")
        return timeslotService.findAllTimeslots().map(Timeslot::toDto)
    }

    @Operation(summary = "Retrieve timeslot by it's id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Found timeslot", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = TimeslotDTO::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "Not found", content = [Content()])
        ]
    )
    @GetMapping("/{id}")
    fun getTimeslotById(@PathVariable id: Long): TimeslotDTO {
        MDC.put("timeslotRequest", id.toString())
        logger.info("GET /timeslot/$id findTimeslotById")
        return timeslotService
            .findTimeslotById(id)
            .orElseThrow { NotFoundException("Timeslot not found with id: $id") }
            .toDto()
    }

    @Operation(summary = "Create new timeslot")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Created timeslot", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = TimeslotDTO::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "Invalid request", content = [Content()]),
        ]
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = CreateTimeslotDTO::class)
        )]
    )
    @PostMapping("/")
    fun createTimeslot(@Valid @RequestBody timeslot: CreateTimeslotDTO): TimeslotDTO {
        val timeslot = timeslotService.createTimeslot(timeslot).toDto()
        MDC.put("timeslotRequest", timeslot.id.toString())
        logger.info("PUT /timeslot createTimeslot")
        return timeslot
    }

    @Operation(summary = "Update existing timeslot")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Updated timeslot", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = TimeslotDTO::class)
                )]
            ),
            ApiResponse(responseCode = "404", description = "Timeslot cannot be found", content = [Content()]),
            ApiResponse(responseCode = "400", description = "Invalid request", content = [Content()]),
        ]
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = UpdateTimeslotDTO::class)
        )]
    )
    @PutMapping("/{id}")
    fun updateTimeslot(@PathVariable id: Long, @Valid @RequestBody timeslot: UpdateTimeslotDTO) {
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