package ua.edu.ukma.dudes.scheduleMeBaby

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ua.edu.ukma.dudes.scheduleMeBaby.dto.Days
import ua.edu.ukma.dudes.scheduleMeBaby.service.ScheduleXLSXParser
import java.io.File
import java.io.FileInputStream


@SpringBootTest
//@ExtendWith(SpringExtension)
class ScheduleParserTest(@Autowired private val scheduleXLSXParser: ScheduleXLSXParser) {


    @Test
    fun `should parse the schedule correctly`() {
        val pathToFile = "src\\test\\resources\\testData\\FI_IPZ_4_2021.xlsx"
        val fileToRead = File(pathToFile)
        val fileInputStream = FileInputStream(fileToRead)
        assertDoesNotThrow {
            val scheduleDTO = scheduleXLSXParser.readFromExcel(fileInputStream)
            assert(scheduleDTO != null)
            assert(scheduleDTO?.faculty == "Факультет інформатики")
            assert(scheduleDTO?.studyYear == 4)
            assert(scheduleDTO?.years == "2021-2022")
            assert(scheduleDTO!!.days[0].dayEntries.size == 7)
        }
    }
}