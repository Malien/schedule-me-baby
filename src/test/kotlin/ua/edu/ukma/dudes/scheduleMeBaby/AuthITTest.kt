package ua.edu.ukma.dudes.scheduleMeBaby

import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import ua.edu.ukma.dudes.scheduleMeBaby.entity.User
import ua.edu.ukma.dudes.scheduleMeBaby.repository.UserRepository
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto.AuthUserDTO
import ua.edu.ukma.dudes.scheduleMeBaby.security.user.dto.UserCredentials

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthITTest {
    @Autowired
    private lateinit var repository: UserRepository
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @LocalServerPort
    fun savePort(port: Int) {
        RestAssured.port = port
    }

    @BeforeEach
    fun setup() {
        repository.deleteAll()
        repository.save(User("admin", "admin"))
    }

    @Test
    fun shouldSuccessfullyAuthenticate() {
        val authUserDTO = RestAssured
            .given()
            .body(objectMapper.writeValueAsString(UserCredentials("admin", "admin")))
            .contentType(ContentType.JSON)
            .`when`()
            .post("/auth/login")
            .then()
            .extract()
            .`as`(AuthUserDTO::class.java)
        assert(authUserDTO.token.isNotBlank())
    }

    @Test
    fun shouldNotSuccessfullyAuthenticate() {
        RestAssured
            .given()
            .body(objectMapper.writeValueAsString(UserCredentials("invalid", "invalid")))
            .contentType(ContentType.JSON)
            .`when`()
            .post("/auth/login")
            .then()
            .statusCode(401)
    }
}