package ua.edu.ukma.dudes.schedulemebaby.entity

import javax.persistence.*

@Entity
@Table(name = "teachers")
class Teacher(
    @Column(nullable = false)
    var name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id", nullable = false)
    var teacherId: Long? = null
}
