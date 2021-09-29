package ua.edu.ukma.dudes.schedulemebaby.entity

import javax.persistence.*

@Entity
@Table(name = "teachers")
data class Teacher(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null, var name: String)
