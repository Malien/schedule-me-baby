package ua.edu.ukma.dudes.scheduleMeBaby.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Timeslot

interface TimeslotRepository : CrudRepository<Timeslot, Long> {
    @Query(value = "SELECT * FROM timeslots t WHERE t.group_id = :groupId", nativeQuery = true)
    fun findAllByGroupId(@Param("groupId") groupId: Long): Iterable<Timeslot>
}
