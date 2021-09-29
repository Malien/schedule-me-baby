package ua.edu.ukma.dudes.scheduleMeBaby.entity

import javax.persistence.*


@Table(name="subject")
@Entity
class Subject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id", nullable = false)
    var subject_id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,
) {
}