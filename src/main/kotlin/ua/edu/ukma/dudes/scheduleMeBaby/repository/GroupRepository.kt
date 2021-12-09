package ua.edu.ukma.dudes.scheduleMeBaby.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Group
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Subject
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher

interface GroupRepository : CrudRepository<Group, Long> {
    @Query(value = "SELECT * FROM groups g WHERE g.subject_id = :subjectId ORDER BY g.number", nativeQuery = true)
    fun findAllBySubjectId(@Param("subjectId") subjectId: Long): Iterable<Group>

    @Query(value = """
        SELECT * FROM groups g 
        JOIN students_groups sg ON g.group_id = sg.group_id 
        WHERE sg.student_id = :studentId
    """, nativeQuery = true)
    fun findAllByStudentId(studentId: Long): Iterable<Group>


    @Query(
        value = """
        SELECT g FROM Group g
        WHERE g.subject = :subject AND
                g.teacher = :teacher AND 
                g.number = :number AND 
                g.type = :type
    """
    )
    fun findAllByAllFields(
        @Param("subject") subject: Subject, @Param("teacher") teacher: Teacher,
        @Param("number") number: Int, @Param("type") type: Int
    ): List<Group>
}
