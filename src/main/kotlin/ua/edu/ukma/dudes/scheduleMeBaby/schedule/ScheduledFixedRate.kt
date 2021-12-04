package ua.edu.ukma.dudes.scheduleMeBaby.schedule

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableAsync
class ScheduledFixedRate {

    private val logger = LoggerFactory.getLogger(ScheduledFixedRate::class.java)

    @Async
    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    fun scheduleFixedRateTaskAsync() {
        val now = System.currentTimeMillis() / 1000
        logger.info("Scheduled async fixed rate task - $now")
        logger.info("Sending heartbeat signal...")
        Thread.sleep(10000)
    }
}