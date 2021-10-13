package ua.edu.ukma.dudes.scheduleMeBaby.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.edu.ukma.dudes.scheduleMeBaby.dto.TimeslotDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot
import ua.edu.ukma.dudes.scheduleMeBaby.service.CreateTimeslotDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.TimeslotService

@RestController
@RequestMapping("/timeslot")
class TimeslotController(private val timeslotService: TimeslotService) {

    // TODO: Filtering via query params
    @GetMapping("/")
    fun get(): ResponseEntity<Iterable<TimeslotDTO>> =
        ResponseEntity.ok(timeslotService.findAllTimeslots().map(Timeslot::toDto))

    @GetMapping("/{id}")
    fun getGroupsByID(@PathVariable id: Long): ResponseEntity<TimeslotDTO> {
        val optional = timeslotService.findTimeslotByID(id)
        return if (optional.isPresent)
            ResponseEntity.ok(optional.get().toDto())
        else
            ResponseEntity.notFound().build()
    }

    @PutMapping("/")
    fun createGroup(@RequestBody timeslot: CreateTimeslotDTO): TimeslotDTO =
        timeslotService.createTimeslot(timeslot).toDto()

//    @PatchMapping("/{id}")
//    fun updateGroup(@PathVariable id: Long, @RequestBody group: UpdateGroupDTO) =
//        groupService.updateGroup(group)

    @DeleteMapping("/{id}")
    fun deleteGroupById(@PathVariable id: Long) = timeslotService.deleteTimeslotByID(id)

}