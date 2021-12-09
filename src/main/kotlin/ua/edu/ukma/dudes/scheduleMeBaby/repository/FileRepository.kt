package ua.edu.ukma.dudes.scheduleMeBaby.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ua.edu.ukma.dudes.scheduleMeBaby.entity.FileEntity

@Repository
interface FileRepository: JpaRepository<FileEntity, Long>