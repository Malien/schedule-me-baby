package ua.edu.ukma.dudes.scheduleMeBaby

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ua.edu.ukma.dudes.scheduleMeBaby.controller.api.StudentController
import ua.edu.ukma.dudes.scheduleMeBaby.dto.StudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.CreateStudentDTO
import ua.edu.ukma.dudes.scheduleMeBaby.service.StudentService
import java.util.stream.Stream

@WebMvcTest(StudentController::class)
class StudentControllerTest(
    @Autowired
    private val mockMvc: MockMvc,
    @Autowired
    private val objectMapper: ObjectMapper,
) {
    @MockBean
    private lateinit var studentService: StudentService

    @Test
    fun shouldHandleGetStudentsRequest() {
        val students = listOf(
            StudentDTO(1, "Tony"),
            StudentDTO(2, "Vlad"),
            StudentDTO(3, "Yarik"),
            StudentDTO(4, "Petya"),
            StudentDTO(5, "Vasya"),
        )
        Mockito.`when`(studentService.findAllStudents()).thenReturn(students)
        mockMvc.perform(
            MockMvcRequestBuilders.get("/student/")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(students)))
        verify(studentService, times(1)).findAllStudents()
    }

    @Test
    fun shouldThrowException_whenGetStudentNotFound() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/student/1000")
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun shouldHandleCreateStudentRequest() {
        val studentCreate = CreateStudentDTO("Tony")
        val studentReturn = StudentDTO(1, studentCreate.name);
        Mockito.`when`(studentService.createStudent(studentCreate)).thenReturn(studentReturn)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/student/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentCreate))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(studentReturn)))
        verify(studentService, times(1)).createStudent(studentCreate)
    }

    @Test
    fun shouldThrowException_whenCreateStudentMissingRequestFields() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/student/")
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
        verify(studentService, never()).createStudent(MockitoHelper.anyObject())
    }

    @ParameterizedTest
    @MethodSource("studentsList")
    fun shouldHandleCreateStudentsRequest(student: CreateStudentDTO) {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/student/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
        verify(studentService, times(1)).createStudent(student)
    }

    companion object {
        @JvmStatic
        fun studentsList(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(CreateStudentDTO("Tony")),
                Arguments.of(CreateStudentDTO("Vlad")),
                Arguments.of(CreateStudentDTO("Yarik")),
                Arguments.of(CreateStudentDTO("Petya")),
                Arguments.of(CreateStudentDTO("Vasya")),
            )
        }
    }
}

object MockitoHelper {
    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}
