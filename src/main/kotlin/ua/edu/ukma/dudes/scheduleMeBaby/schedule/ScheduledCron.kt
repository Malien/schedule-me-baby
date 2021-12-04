package ua.edu.ukma.dudes.scheduleMeBaby.schedule

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ua.edu.ukma.dudes.scheduleMeBaby.repository.UserRepository

@Component
class ScheduledCron(
    private val userRepository: UserRepository
) {
    private val logger = LoggerFactory.getLogger(ScheduledCron::class.java)

    @Scheduled(cron = "\${scheduled.cron.value}", zone = "Europe/Kiev")
    fun scheduleTaskUsingCronExpression() {
        val now = System.currentTimeMillis() / 1000
        logger.info("Scheduled cron task - $now")
        logger.info("${userRepository.count()} users registered!")
    }
}