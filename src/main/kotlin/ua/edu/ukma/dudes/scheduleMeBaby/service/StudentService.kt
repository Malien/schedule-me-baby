package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.springframework.stereotype.Service
import ua.edu.ukma.dudes.scheduleMeBaby.exception.NotFoundException
import ua.edu.ukma.dudes.scheduleMeBaby.repository.GroupRepository
import ua.edu.ukma.dudes.scheduleMeBaby.repository.UserRepository

@Service
class StudentService(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
) {

    fun enrollGroup(studentId: Long, groupId: Long, enroll: Boolean): Boolean {
        val student = userRepository.findById(studentId).orElseThrow {
            NotFoundException("Student with id $studentId does not exist")
        }
        val group = groupRepository.findById(groupId).orElseThrow {
            NotFoundException("Group with id $groupId does not exist")
        }
        val result = if (enroll)
            student.studentGroups.add(group)
        else student.studentGroups.remove(group)
        userRepository.save(student)
        return result
    }

}