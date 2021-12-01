package ua.edu.ukma.dudes.scheduleMeBaby.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class ExecutionTimeLogger {
    private val logger = LoggerFactory.getLogger(ExecutionTimeLogger::class.java)


    @Around("@annotation(ua.edu.ukma.dudes.scheduleMeBaby.aop.annotation.LogExecutionTime)")
    fun logExecutionTime(joinPoint: ProceedingJoinPoint): Any {
        val start = System.currentTimeMillis()
        val proceed = joinPoint.proceed()
        val executionTime = System.currentTimeMillis() - start
        logger.info(joinPoint.signature.toString() + " executed in " + executionTime + "ms")
        return proceed
    }
}