package ua.edu.ukma.dudes.scheduleMeBaby.controller.pages

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import ua.edu.ukma.dudes.scheduleMeBaby.controller.isAdmin
import ua.edu.ukma.dudes.scheduleMeBaby.dto.*
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.GroupService
import ua.edu.ukma.dudes.scheduleMeBaby.service.SubjectService
import ua.edu.ukma.dudes.scheduleMeBaby.service.TimeslotService
import java.security.Principal

@Controller
@RequestMapping("/timeslots")
class TimeslotsPagesController(
    private val subjectService: SubjectService,
    private val groupService: GroupService,
    private val timeslotService: TimeslotService,
) {

    @GetMapping("/{groupId}")
    fun listSubjects(@PathVariable groupId: Long, model: Model, principal: Principal?): String {
        val group = groupService.findGroupById(groupId).orElseThrow {
            NotFoundException("Group with id $groupId does not exist")
        }
        model.addAttribute("isAdmin", true)
        model.addAttribute("group", group.toUIDto())
        model.addAttribute("subject", group.subject.toDto())
        model.addAttribute("timeslots", timeslotService.findAllTimeslots(groupId).map(Timeslot::toUIDto))
        model["isAdmin"] = principal.isAdmin
        return "timeslots"
    }

    @PostMapping(path = ["/"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    @PreAuthorize("hasRole('ADMIN')")
    fun newSubject(
        createTimeslotDTO: CreateTimeslotFormDTO,
        model: Model,
        principal: Principal?
    ): String {
        timeslotService.createTimeslot(CreateTimeslotDTO(
            createTimeslotDTO.groupId,
            dayStringToInt(createTimeslotDTO.day),
            createTimeslotDTO.clazz,
            createTimeslotDTO.auditorium,
            createTimeslotDTO.weeks.joinToString(separator = ","),
        ))
        return "redirect:/timeslots/${createTimeslotDTO.groupId}"
    }

    @PostMapping(path = ["/{groupId}/delete/{timeslotId}"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun deleteSubject(@PathVariable groupId: Long, @PathVariable timeslotId: Long, model: Model, principal: Principal?): String {
        timeslotService.deleteTimeslotById(timeslotId)
        return "redirect:/timeslots/${groupId}"
    }
}