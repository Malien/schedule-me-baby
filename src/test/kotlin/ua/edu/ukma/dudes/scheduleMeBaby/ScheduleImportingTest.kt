package ua.edu.ukma.dudes.scheduleMeBaby

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Role
import ua.edu.ukma.dudes.scheduleMeBaby.entity.User
import ua.edu.ukma.dudes.scheduleMeBaby.repository.*
import ua.edu.ukma.dudes.scheduleMeBaby.service.ScheduleService
import ua.edu.ukma.dudes.scheduleMeBaby.service.ScheduleXLSXParser
import java.io.File
import java.io.FileInputStream

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScheduleImportingTest(
    @Autowired private val scheduleService: ScheduleService,
    @Autowired private val scheduleXLSXParser: ScheduleXLSXParser,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val fileRepository: FileRepository,
    @Autowired private val groupRepository: GroupRepository,
    @Autowired private val subjectRepository: SubjectRepository,
    @Autowired private val teacherRepository: TeacherRepository,
    @Autowired private val timeslotRepository: TimeslotRepository
) {

    @BeforeAll
    fun setup(
        @Autowired roleRepository: RoleRepository,
        @Autowired passwordEncoder: BCryptPasswordEncoder
    ) {
        val roleAdmin = Role("ROLE_ADMIN")
        val roleUser = Role("ROLE_USER")

        roleRepository.save(roleAdmin)
        roleRepository.save(roleUser)

        userRepository.save(
            User(
                "Admin", "test_admin", passwordEncoder.encode("test_admin"),
                mutableSetOf(roleAdmin)
            )
        )
        userRepository.save(
            User(
                "User", "test_user", passwordEncoder.encode("test_user"),
                mutableSetOf(roleUser)
            )
        )
    }

    @Test
    fun `file should be saved both to filesystem and to DB`() {
        val pathToFile = "src\\test\\resources\\testData\\FI_IPZ_4_2021.xlsx"
        val fileToRead = File(pathToFile)
        val fileInputStream = FileInputStream(fileToRead)

//        val userPrincipal = Mockito.mock(UserPrincipal::class.java)
//        Mockito.`when`(userPrincipal.userEntity).thenReturn(User("user", "user", "user"))
        val user = userRepository.findByLogin("test_user")
        assert(user != null)
        user!!

        val fileName = "testSchedule1"
        val fileId = scheduleService.saveFile(fileInputStream, user, fileName)
        assert(fileId != null)

        val file = File("src\\main\\resources\\static\\schedules\\$fileName")
        assert(file.exists())
        file.delete()

        assert(fileRepository.existsById(fileId!!))
    }

    @Test
    fun `should found file in DB by filename`(){
        val pathToFile = "src\\test\\resources\\testData\\FI_IPZ_4_2021.xlsx"
        val fileToRead = File(pathToFile)
        val fileInputStream = FileInputStream(fileToRead)

        val user = userRepository.findByLogin("test_user")
        assert(user != null)
        user!!

        val fileName = "testSchedule2"
        val fileId = scheduleService.saveFile(fileInputStream, user, fileName)
        assert(fileId != null)
        val file = File("src\\main\\resources\\static\\schedules\\$fileName")
        assert(file.exists())
        file.delete()

        val fileEntities =
            fileRepository.findAllByFilenameContainingIgnoreCaseOrderByFilename(fileName)
        assert(fileEntities[0].fileId == fileRepository.findById(fileId!!).get().fileId)
    }


    @Test
    fun `should save all data from file`(){
        val pathToFile = "src\\test\\resources\\testData\\FI_IPZ_4_2021.xlsx"
        val fileToRead = File(pathToFile)
        val fileInputStream = FileInputStream(fileToRead)

        scheduleService.save(scheduleXLSXParser.readFromExcel(fileInputStream)!!)

        assert(subjectRepository.findAll().count() == 7)
        assert(teacherRepository.findAll().count() == 9)
        assert(groupRepository.findAll().count() == 32)
        assert(timeslotRepository.findAll().count() == 39)
    }
}