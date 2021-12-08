package ua.edu.ukma.dudes.scheduleMeBaby

import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCache

private val logger = LoggerFactory.getLogger(SMBBCacheManager::class.java)

class SMBBCacheManager: CacheManager {
    private val teacherCache = ConcurrentMapCache("teacher", true)

    override fun getCache(name: String): Cache? {
        logger.info("Requested cache $name")
        if (name == "teacher") return teacherCache
        return null
    }

    override fun getCacheNames(): MutableCollection<String> {
        return mutableListOf("teacher")
    }
}