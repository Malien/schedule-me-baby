package ua.edu.ukma.dudes.scheduleMeBaby.entity

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

    override fun toString() = "Teacher(name='$name', teacherId=$teacherId)"

    override fun equals(other: Any?) =
        this === other || (other is Teacher && name == other.name && teacherId == other.teacherId)

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (teacherId?.hashCode() ?: 0)
        return result
    }

}
