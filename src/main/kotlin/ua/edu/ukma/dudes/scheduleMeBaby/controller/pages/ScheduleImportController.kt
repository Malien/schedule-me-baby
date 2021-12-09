package ua.edu.ukma.dudes.scheduleMeBaby.controller.pages

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ua.edu.ukma.dudes.scheduleMeBaby.controller.isAdmin
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.UserPrincipal
import ua.edu.ukma.dudes.scheduleMeBaby.service.ScheduleService
import ua.edu.ukma.dudes.scheduleMeBaby.service.ScheduleXLSXParser
import java.security.Principal

@Controller
@RequestMapping("/import")
class ScheduleImportController(
    private val scheduleXLSXParser: ScheduleXLSXParser,
    private val scheduleService: ScheduleService
) {
    val logger = LoggerFactory.getLogger(SubjectPagesController::class.java)

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    fun importView(@RequestParam(value = "nameFilter", required = false) nameFilter: String?,
                   model: Model, principal: Principal?): String {
        val isAdmin = principal?.isAdmin ?: false
        if (!isAdmin) {
            model["error"] = "You do not have permissions to access subject edit page"
            return "schedule"
        }
        model.addAttribute("isAdmin", isAdmin)
        model.addAttribute("files", scheduleService.findAllFiles(nameFilter ?: ""))
        model.addAttribute("fileNameFilter", nameFilter)
        return "import"
    }

    @PostMapping(path = [""], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    fun importSchedule(
        @RequestParam(value = "file", required = true) file: MultipartFile,
        model: Model, principal: Principal?
    ): String =
        protectedAction(model, principal) {
            logger.info("Filename: ${file.originalFilename}")
            // parse file hire
            val scheduleDTO = scheduleXLSXParser.readFromExcel(file.inputStream)
            if (scheduleDTO == null)
                model["error"] = "Something went wrong... Check the format of your schedule."
            else {
                val token = principal as UsernamePasswordAuthenticationToken
                val user = token.principal as UserPrincipal
                if (scheduleService.saveFile(file.inputStream, user.userEntity,
                    file.originalFilename ?:
                    "${scheduleDTO.faculty}_${scheduleDTO.speciality}_${scheduleDTO.studyYear}_${scheduleDTO.years}"
                        .replace(" ", "_")) != null
                ) {
                    scheduleService.save(scheduleDTO)
                } else {
                    model["error"] = "The file with the given name already exists."
                }
            }
            model.addAttribute("files", scheduleService.findAllFiles(""))
        }

    @PostMapping(path = ["/{id}/delete"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    fun deleteSchedule(@PathVariable id: Long, model: Model, principal: Principal?) =
        protectedAction(model, principal) {
            scheduleService.deleteSchedule(id)
            model.addAttribute("files", scheduleService.findAllFiles(""))
        }

    fun protectedAction(model: Model, principal: Principal?, block: () -> Unit): String {
        val isAdmin = principal?.isAdmin ?: false
        logger.info("Import schedule: isAdmin=$isAdmin")
        return if (!isAdmin) {
            model["error"] = "You do not have enough permissions to import a schedule"
            "redirect:/schedule"
        } else try {
            block()
            "import"
        } catch (e: Exception) {
            logger.error(e.stackTraceToString())
            model["error"] = e.message ?: "Unexpected error occurred"
            "import"
        }
    }
}