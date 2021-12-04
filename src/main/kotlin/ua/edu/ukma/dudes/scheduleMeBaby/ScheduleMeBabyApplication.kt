package ua.edu.ukma.dudes.scheduleMeBaby

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@OpenAPIDefinition(
    info = Info(
        title = "Schedule me baby",
        version = "0.0.1-dev",
        description = "A service for scheduling course timetables"
    ),
//    tags = [
//        Tag(name = "Student", description = "Student specific APIs")
//    ]
)
@SpringBootApplication
@EnableScheduling
class ScheduleMeBabyApplication

fun main(args: Array<String>) {
    runApplication<ScheduleMeBabyApplication>(*args)
}
