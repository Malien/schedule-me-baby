package ua.edu.ukma.dudes.scheduleMeBaby.service

import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ua.edu.ukma.dudes.scheduleMeBaby.dto.Day
import ua.edu.ukma.dudes.scheduleMeBaby.dto.DayEntry
import ua.edu.ukma.dudes.scheduleMeBaby.dto.Days
import ua.edu.ukma.dudes.scheduleMeBaby.dto.ScheduleDTO
import java.io.IOException


@Service
class ScheduleXLSXParser {

    private val logger = LoggerFactory.getLogger(ScheduleXLSXParser::class.java)

    @Throws(IOException::class)
    fun readFromExcel(file: MultipartFile): ScheduleDTO? {
        val myExcelBook = XSSFWorkbook(file.inputStream)
        val myExcelSheet: XSSFSheet = myExcelBook.getSheetAt(0)

        var faculty: String? = null
        var speciality: String? = null
        var studyYear: Int? = null
        var years: String? = null

        var parseDay = false
        var scheduleDTO: ScheduleDTO? = null
        var day: Day? = null
        var lastTime : String? = null
        for (row in myExcelSheet.rowIterator()) {
            if (row.rowNum >= 150) break

            if (row.getCell(0).cellType === CellType.STRING) {
                val value: String = row.getCell(0).stringCellValue

                if (value.lowercase().contains("факультет")) {
                    faculty = value
                } else if (value.lowercase().contains("спеціальність")) {
                    speciality = value.split('"')[1]
                    studyYear = Integer.parseInt(value.split(',')[1].trim().split(' ')[0])
                } else if (value.lowercase().contains("семестр")) {
                    val split = value.split(' ')
                    years = split[split.size - 2]
                }

                if (parseDay) {
                    logger.info("day: $value")
                    day = Day(Days.mapValue(value)!!)
                    scheduleDTO!!.days.add(day)
                    try {
                        val rowEntries = getRowEntries(row, lastTime) ?: break
                        lastTime = rowEntries.time
                        day.dayEntries.add(rowEntries)
                    } catch (e: IndexOutOfBoundsException) {
                        continue
                    }
                }

                if (value.contains("День")) {
                    getRowEntries(row)
                    scheduleDTO = ScheduleDTO(faculty!!, speciality!!, studyYear!!, years!!)
                    parseDay = true
                }
            }

            if (row.getCell(0).cellType === CellType.BLANK && parseDay) {
                try {
                    val rowEntries = getRowEntries(row, lastTime) ?: break
                    lastTime = rowEntries.time
                    day!!.dayEntries.add(rowEntries)
                } catch (e: IndexOutOfBoundsException) {
                    continue
                }

            }
        }
        scheduleDTO?.days?.stream()?.filter{it.dayEntries.size > 0}
        logger.info("faculty=$faculty, speciality=$speciality, studyYear=$studyYear, years=$years")
        logger.info("$scheduleDTO")
        myExcelBook.close()

        return scheduleDTO
    }

    fun getRowEntries(row: Row, lastTime: String? = null): DayEntry? {
        var s = ""
        var time = row.getCell(1).stringCellValue
        time = time.ifBlank { lastTime }
        val subject = row.getCell(2).stringCellValue.split(',')[0]
        val teacher = row.getCell(2).stringCellValue.split(',')[1]
        val groupCell = row.getCell(3)
        val group = if (groupCell.cellType == CellType.STRING) groupCell.stringCellValue else groupCell.numericCellValue
        val weeks = row.getCell(4).stringCellValue
        val auditorium = row.getCell(5).stringCellValue

        for (cell in row.cellIterator()) {
            if (cell.cellType == CellType.STRING)
                s = s.plus("${cell.stringCellValue}; ")
            else if (cell.cellType == CellType.NUMERIC)
                s = s.plus("${cell.numericCellValue}; ")
        }
        logger.info(s)

        return if (subject == "") null else DayEntry(time, subject, teacher, "$group", weeks, auditorium)
    }
}