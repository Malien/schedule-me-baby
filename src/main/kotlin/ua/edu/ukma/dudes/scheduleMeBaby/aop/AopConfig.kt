package ua.edu.ukma.dudes.scheduleMeBaby.aop

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
class AopConfig {
}