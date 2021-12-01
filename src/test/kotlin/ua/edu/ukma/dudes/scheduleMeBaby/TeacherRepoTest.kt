package ua.edu.ukma.dudes.scheduleMeBaby

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ua.edu.ukma.dudes.scheduleMeBaby.entity.Teacher
import ua.edu.ukma.dudes.scheduleMeBaby.repository.TeacherRepository

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherRepoTest(@Autowired private val teacherRepo: TeacherRepository) {

    @Test
    fun `should retrieve inserted teacher`() {
        val teacher = teacherRepo.save(Teacher(name="Zina"))
        assert(teacher.teacherId != null)
        val found = teacherRepo.findById(teacher.teacherId!!)
        assert(found.isPresent)
        assert(found.get() == teacher)
        teacherRepo.delete(teacher)
    }

    @Test
    fun `is able to modify teacher's name`() {
        val teacher = teacherRepo.save(Teacher(name = "Zinaїda"))
        assert(teacher.teacherId != null)
        teacher.name = "Zinaїda the Sequel"
        teacherRepo.save(teacher)
        val found = teacherRepo.findById(teacher.teacherId!!)
        assert(found.isPresent)
        assert(found.get().name == "Zinaїda the Sequel")
        teacherRepo.delete(found.get())
    }

}