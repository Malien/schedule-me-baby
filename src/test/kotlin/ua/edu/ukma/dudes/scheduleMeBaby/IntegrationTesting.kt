package ua.edu.ukma.dudes.scheduleMeBaby

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import ua.edu.ukma.dudes.scheduleMeBaby.entity.*
import ua.edu.ukma.dudes.scheduleMeBaby.repository.*
import ua.edu.ukma.dudes.scheduleMeBaby.security.service.AuthService
import ua.edu.ukma.dudes.scheduleMeBaby.security.service.TokenService
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto.UserCredentials
import ua.edu.ukma.dudes.scheduleMeBaby.service.SubjectService

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTesting(
    @Autowired
    private val subjectService: SubjectService,
    @Autowired
    private val authService: AuthService,
    @Autowired
    private val tokenService: TokenService,
) {

    @LocalServerPort
    fun savePort(port: Int) {
        RestAssured.port = port
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup(
            @Autowired
            userRepository: UserRepository,
            @Autowired
            roleRepository: RoleRepository,
            @Autowired
            teacherRepository: TeacherRepository,
            @Autowired
            subjectRepository: SubjectRepository,
            @Autowired
            groupRepository: GroupRepository,
            @Autowired
            timeslotRepository: TimeslotRepository,
        ) {
            val roleAdmin = Role("ROLE_ADMIN")
            val roleUser = Role("ROLE_USER")

            roleRepository.save(roleAdmin)
            roleRepository.save(roleUser)

            userRepository.save(User("Admin", "admin", "admin", mutableSetOf(roleAdmin)))
            userRepository.save(User("User", "user", "user", mutableSetOf(roleUser)))

            val teacherTony = Teacher("Tony")
            val teacherVlad = Teacher("Vlad")
            val teacherYarik = Teacher("Yarik")

            val subjectMath = Subject("Math")
            val subjectLinearAlgebra = Subject("Linear Algebra")
            val subjectJavaProgramming = Subject("Java Programming")
            val subjectCppProgramming = Subject("C++ Programming")
            val subjectSpringBoot = Subject("Spring Boot")

            val groupMath11 = Group(1, 1, subjectMath, teacherTony)
            val groupMath12 = Group(1, 2, subjectMath, teacherTony)

            val groupLinearAlgebra11 = Group(1, 1, subjectLinearAlgebra, teacherTony)
            val groupLinearAlgebra12 = Group(1, 2, subjectLinearAlgebra, teacherTony)
            val groupLinearAlgebra22 = Group(2, 2, subjectLinearAlgebra, teacherTony)

            val groupJavaProgramming11 = Group(1, 1, subjectJavaProgramming, teacherVlad)
            val groupJavaProgramming12 = Group(1, 2, subjectJavaProgramming, teacherVlad)
            val groupJavaProgramming22 = Group(2, 2, subjectJavaProgramming, teacherVlad)
            val groupJavaProgramming32 = Group(3, 2, subjectJavaProgramming, teacherVlad)

            val groupCppProgramming11 = Group(1, 1, subjectCppProgramming, teacherYarik)
            val groupCppProgramming12 = Group(1, 2, subjectCppProgramming, teacherYarik)
            val groupCppProgramming22 = Group(2, 2, subjectCppProgramming, teacherYarik)
            val groupCppProgramming32 = Group(3, 2, subjectCppProgramming, teacherYarik)

            val groupSpringBoot11 = Group(1, 1, subjectSpringBoot, teacherVlad)
            val groupSpringBoot12 = Group(1, 2, subjectSpringBoot, teacherVlad)
            val groupSpringBoot22 = Group(2, 2, subjectSpringBoot, teacherVlad)
            val groupSpringBoot32 = Group(3, 2, subjectSpringBoot, teacherVlad)

            teacherRepository.save(teacherTony)
            teacherRepository.save(teacherVlad)
            teacherRepository.save(teacherYarik)

            subjectRepository.save(subjectMath)
            subjectRepository.save(subjectLinearAlgebra)
            subjectRepository.save(subjectJavaProgramming)
            subjectRepository.save(subjectCppProgramming)
            subjectRepository.save(subjectSpringBoot)

            groupRepository.save(groupMath11)
            groupRepository.save(groupMath12)

            groupRepository.save(groupLinearAlgebra11)
            groupRepository.save(groupLinearAlgebra12)
            groupRepository.save(groupLinearAlgebra22)

            groupRepository.save(groupJavaProgramming11)
            groupRepository.save(groupJavaProgramming12)
            groupRepository.save(groupJavaProgramming22)
            groupRepository.save(groupJavaProgramming32)

            groupRepository.save(groupCppProgramming11)
            groupRepository.save(groupCppProgramming12)
            groupRepository.save(groupCppProgramming22)
            groupRepository.save(groupCppProgramming32)

            groupRepository.save(groupSpringBoot11)
            groupRepository.save(groupSpringBoot12)
            groupRepository.save(groupSpringBoot22)
            groupRepository.save(groupSpringBoot32)
        }
    }

    @Test
    fun `should authenticate as admin and user`() {
        val authAdminDTO = authService.login(UserCredentials("admin", "admin"))
        assert(tokenService.getUsernameFromToken(authAdminDTO.token) == "admin")
        assert(authAdminDTO.token.isNotEmpty())
        assert(authAdminDTO.userDTO.roles.size == 1)
        assert(authAdminDTO.userDTO.roles.toList()[0].roleName == "ROLE_ADMIN")

        val authUserDTO = authService.login(UserCredentials("user", "user"))
        assert(tokenService.getUsernameFromToken(authUserDTO.token) == "user")
        assert(authUserDTO.token.isNotEmpty())
        assert(authUserDTO.userDTO.roles.size == 1)
        assert(authUserDTO.userDTO.roles.toList()[0].roleName == "ROLE_USER")
    }

    @Test
    fun `subject service should correctly return subjects`() {
        assert(subjectService.findAllSubjects().toList().size == 5);
        assert(subjectService.findSubjectById(1).get().name == "Math")
        assert(subjectService.findAllSubjects("pr").toList().size == 3)
    }

}