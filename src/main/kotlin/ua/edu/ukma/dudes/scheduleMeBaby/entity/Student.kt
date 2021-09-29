package ua.edu.ukma.dudes.scheduleMeBaby.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "students")
data class Student(@Id @GeneratedValue var id: Int, var name: String)