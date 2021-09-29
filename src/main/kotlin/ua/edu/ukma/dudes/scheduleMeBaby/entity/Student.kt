package ua.edu.ukma.dudes.scheduleMeBaby.entity

import javax.persistence.*

@Entity
@Table(name = "students")
data class Student(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int, var name: String)