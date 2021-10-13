package ua.edu.ukma.dudes.schedulemebaby.entity

import javax.persistence.*

@Entity
@Table(name = "timeslots")
class Timeslot(
    @Column(name = "day", nullable = false)
    val day: Int,
    @Column(name = "class", nullable = false)
    val clazz: Int,
    @Column(name = "auditorium", nullable = false)
    val auditorium: String,
    @Column(name = "weeks", nullable = false)
    val weeks: String,
) {

    @Id
    @Column(name = "timeslot_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val timeslotId: Long? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    lateinit var group: Group
}