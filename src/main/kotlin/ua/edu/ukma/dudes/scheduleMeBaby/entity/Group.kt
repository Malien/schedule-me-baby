package ua.edu.ukma.dudes.schedulemebaby.entity

import javax.persistence.*

@Entity
@Table(name = "groups")
class Group(
    @Column(nullable = false)
    val number: Int,
    @Column(nullable = false)
    val type: Int
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    val groupId: Long? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    lateinit var subject: Subject

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    lateinit var teacher: Teacher

    @OneToMany(mappedBy = "group", orphanRemoval = true)
    val timeslot: MutableSet<Timeslot> = mutableSetOf()

    @ManyToMany(mappedBy = "studentGroups")
    var students: MutableSet<Student> = mutableSetOf()
}