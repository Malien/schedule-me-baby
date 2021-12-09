package ua.edu.ukma.dudes.scheduleMeBaby.controller.pages

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
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
    fun importView(model: Model, principal: Principal?): String {
        val isAdmin = principal?.isAdmin ?: false
        if (!isAdmin) {
            model["error"] = "You do not have permissions to access subject edit page"
            model.addAttribute("files", scheduleService.findAllFiles())
            return "schedule"
        }
//        model.addAttribute("subject", subject.get())
        return "import"
    }

    @PostMapping(path = [""], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun importSchedule(
        @RequestParam(value = "file", required = true) file: MultipartFile,
        model: Model, principal: Principal?
    ): String =
        protectedAction(model, principal) {
            logger.info("Filename: ${file.originalFilename}")
            // parse file hire
            val scheduleDTO = scheduleXLSXParser.readFromExcel(file)
            return@protectedAction scheduleDTO.toString() + scheduleDTO?.days
//            if (scheduleDTO == null)
//                model["error"] = "Something went wrong... Check the format of your schedule."
//            else {
//                val token = principal as UsernamePasswordAuthenticationToken
//                val user = token.details as UserPrincipal
//                scheduleService.save(scheduleDTO)
//                scheduleService.saveFile(file, user,
//                    file.originalFilename ?:
//                    "${scheduleDTO.faculty}_${scheduleDTO.speciality}_${scheduleDTO.studyYear}_${scheduleDTO.years}"
//                        .replace(" ", "_"))
//            }
        }

    @DeleteMapping(path = ["/{id}"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun deleteSchedule(@PathVariable id: Long, model: Model, principal: Principal?) =
        protectedAction(model, principal) {
            scheduleService.deleteSchedule(id)
            return@protectedAction ""
        }

    fun protectedAction(model: Model, principal: Principal?, block: () -> String): String {
        val isAdmin = principal?.isAdmin ?: false
        logger.info("Import schedule: isAdmin=$isAdmin")
        return if (!isAdmin) {
            model["error"] = "You do not have enough permissions to import a schedule"
            "redirect:/schedule"
        } else try {
            block()
//            "redirect:/import"
        } catch (e: Exception) {
            logger.error(e.stackTraceToString())
            model["error"] = e.message ?: "Unexpected error occurred"
            "import"
        }
    }
}