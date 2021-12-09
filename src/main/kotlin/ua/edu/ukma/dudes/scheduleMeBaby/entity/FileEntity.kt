package ua.edu.ukma.dudes.scheduleMeBaby.entity

import javax.persistence.*

@Entity
@Table(name = "files")
class FileEntity(
    @Column(nullable = false, unique = true)
    val filename: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", nullable = false)
    var fileId: Long? = null
}