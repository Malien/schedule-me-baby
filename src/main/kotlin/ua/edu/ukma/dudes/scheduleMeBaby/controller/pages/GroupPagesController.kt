package ua.edu.ukma.dudes.scheduleMeBaby.controller.pages

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import ua.edu.ukma.dudes.scheduleMeBaby.controller.isAdmin
import ua.edu.ukma.dudes.scheduleMeBaby.dto.CreateGroupDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.CreateGroupFormDTO
import ua.edu.ukma.dudes.scheduleMeBaby.dto.toUIDto
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.service.GroupService
import ua.edu.ukma.dudes.scheduleMeBaby.service.StudentService
import ua.edu.ukma.dudes.scheduleMeBaby.service.SubjectService
import ua.edu.ukma.dudes.scheduleMeBaby.service.TeacherService
import java.security.Principal

@Controller
@RequestMapping("/groups")
class GroupPagesController(
    private val subjectService: SubjectService,
    private val teacherService: TeacherService,
    private val groupService: GroupService,
    private val studentService: StudentService
) {

    @GetMapping("/{subjectId}")
    fun listGroups(@PathVariable subjectId: Long, model: Model, principal: Principal?): String {
        val isAdmin = principal?.isAdmin ?: false
        model.addAttribute("isAdmin", true)
        model.addAttribute("subject", subjectService.findSubjectById(subjectId).orElseThrow {
            NotFoundException("Subject with id $subjectId does not exist")
        })
        model.addAttribute("teachers", teacherService.findAllTeachers())
        model.addAttribute("groups", groupService.findAllGroups(subjectId).map(Group::toUIDto))
        model.addAttribute("enrolledGroupIds", listOf(1)) // TODO enrolledGroupIds
        return "groups"
    }

    @PostMapping(path = ["/"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun newSubject(
        createGroupDTO: CreateGroupFormDTO,
        model: Model,
        principal: Principal?
    ): String {
        val isAdmin = principal?.isAdmin ?: false
        val teacher = teacherService.findTeacherByName(createGroupDTO.teacherName).orElseThrow {
            NotFoundException("Teacher with name ${createGroupDTO.teacherName} does not exist")
        }
        groupService.createGroup(
            CreateGroupDTO(
                createGroupDTO.subjectId,
                teacher.id,
                createGroupDTO.number,
                if (createGroupDTO.type == "Lecture") 0 else 1
            )
        )
        return "redirect:/groups/${createGroupDTO.subjectId}"
    }

    @PostMapping(path = ["/{subjectId}/delete/{groupId}"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun deleteSubject(
        @PathVariable subjectId: Long,
        @PathVariable groupId: Long,
        model: Model,
        principal: Principal?
    ): String {
        groupService.deleteGroupById(groupId)
        return "redirect:/groups/${subjectId}"
    }

    @PostMapping(path = ["/enroll/{groupId}"])
    fun enrollSubject(
        @PathVariable groupId: Long,
        principal: Principal?
    ): String {
        return enrollGroup(12, groupId, true) // TODO get user to enroll to group
    }

    @PostMapping(path = ["/unenroll/{groupId}"])
    fun unenrollSubject(
        @PathVariable groupId: Long,
        principal: Principal?
    ): String {
        return enrollGroup(12, groupId, false) // TODO get user to unenroll to group
    }

    fun enrollGroup(studentId: Long, groupId: Long, enroll: Boolean): String {
        val group = groupService.findGroupById(groupId).orElseThrow {
            NotFoundException("Group with id $groupId does not exist")
        }
        studentService.enrollGroup(studentId, groupId, enroll)
        return "redirect:/groups/${group.subject.subjectId}"
    }

}
