package ua.edu.ukma.dudes.schedulemebaby.entity

import javax.persistence.*

@Entity
@Table(name = "students")
class Student(
    @Column(nullable = false)
    var name: String,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "students_groups",
        joinColumns = [JoinColumn(name="student_id")],
        inverseJoinColumns = [JoinColumn(name="group_id")]
    )
    var studentGroups: MutableSet<Group> = mutableSetOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
    var studentId: Long? = null

}