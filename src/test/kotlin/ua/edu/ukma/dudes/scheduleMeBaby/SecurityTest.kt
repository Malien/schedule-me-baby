package ua.edu.ukma.dudes.scheduleMeBaby

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import ua.edu.ukma.dudes.scheduleMeBaby.controller.api.StudentController
import ua.edu.ukma.dudes.scheduleMeBaby.dto.StudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.repository.StudentRepository
import ua.edu.ukma.dudes.scheduleMeBaby.repository.UserRepository
import ua.edu.ukma.dudes.scheduleMeBaby.service.StudentService
import java.util.*

@WebMvcTest(StudentController::class)
class SecurityTest(
    @Autowired
    private val mockMvc: MockMvc,
) {
    @MockBean
    private lateinit var studentService: StudentService

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun userShouldGetStudent() {
        Mockito.`when`(studentService.findStudentById(1)).thenReturn(Optional.of(StudentDTO(1, "Tony")))
        mockMvc.perform(
            MockMvcRequestBuilders.get("/student/1")
        )
    }

    @Test
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun adminShouldGetStudent() {
        Mockito.`when`(studentService.findStudentById(1)).thenReturn(Optional.of(StudentDTO(1, "Tony")))
        mockMvc.perform(
            MockMvcRequestBuilders.get("/student/1")
        )
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun userShouldNotCreateStudent() {
        Assertions.assertThrows(AuthenticationCredentialsNotFoundException::class.java) {
            Mockito.`when`(studentService.createStudent(MockitoHelper.anyObject())).thenReturn(StudentDTO(1, "Tony"))
            mockMvc.perform(
                MockMvcRequestBuilders.post("/student/")
            )
        }
    }

    @Test
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun adminShouldCreateStudent() {
        Mockito.`when`(studentService.createStudent(MockitoHelper.anyObject())).thenReturn(StudentDTO(1, "Tony"))
        mockMvc.perform(
            MockMvcRequestBuilders.post("/student/")
        )
    }

    // TODO remove unused mocks
    @MockBean
    private lateinit var studentRepository: StudentRepository

    @MockBean
    private lateinit var userRepository: UserRepository
}