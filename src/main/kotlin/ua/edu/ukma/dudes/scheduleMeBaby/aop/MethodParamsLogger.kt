package ua.edu.ukma.dudes.scheduleMeBaby.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Aspect
@Component
class MethodParamsLogger {

    private val logger = LoggerFactory.getLogger(ExecutionTimeLogger::class.java)


    @Around("@annotation(ua.edu.ukma.dudes.scheduleMeBaby.aop.annotation.LogMethodParams)")
    fun logExecutionTime(joinPoint: ProceedingJoinPoint): Any {
        logger.info(joinPoint.signature.toString() + " input params: " + Arrays.toString(joinPoint.args))
        val proceed = joinPoint.proceed()
        logger.info(joinPoint.signature.toString() + " output value: " + proceed.toString())
        return proceed
    }
}