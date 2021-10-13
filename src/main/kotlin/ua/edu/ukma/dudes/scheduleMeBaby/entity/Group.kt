package ua.edu.ukma.dudes.scheduleMeBaby.entity

import javax.persistence.*

@Entity
@Table(name = "groups")
class Group(
    @Column(nullable = false)
    val number: Int,

    @Column(nullable = false)
    val type: Int,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    val subject: Subject,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    var teacher: Teacher
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    val groupId: Long? = null

    @OneToMany(mappedBy = "group", orphanRemoval = true)
    val timeslot: MutableSet<Timeslot> = mutableSetOf()

    @ManyToMany(mappedBy = "studentGroups")
    var students: MutableSet<Student> = mutableSetOf()
}