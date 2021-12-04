package ua.edu.ukma.dudes.scheduleMeBaby.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group

interface GroupRepository : CrudRepository<Group, Long> {
    @Query(value = "SELECT * FROM groups g WHERE g.subject_id = :subjectId", nativeQuery = true)
    fun findAllBySubjectId(@Param("subjectId") subjectId: Long): Iterable<Group>
}
