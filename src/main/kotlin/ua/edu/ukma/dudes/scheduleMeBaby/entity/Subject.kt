package ua.edu.ukma.dudes.scheduleMeBaby.entity

import javax.persistence.*


@Table(name="subjects")
@Entity
class Subject(
    @Column(name = "name", nullable = false)
    var name: String,

    @OneToMany(mappedBy = "subject", orphanRemoval = true)
    val groups: MutableSet<Group> = mutableSetOf()
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id", nullable = false)
    var subjectId: Long? = null
}