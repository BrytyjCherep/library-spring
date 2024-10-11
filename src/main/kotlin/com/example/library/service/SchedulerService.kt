package com.example.library.service

import com.example.library.model.BookLog
import com.example.library.repository.BookLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SchedulerService {
    @Autowired
    lateinit var bookLogRepository: BookLogRepository

    @Autowired
    lateinit var emailService: EmailService

    @Value("\${app.returnPeriod}")
    lateinit var returnPeriod1: String

    @Scheduled(cron = "* 16 * * * *")
    fun getListOfOverdueReaders() : List<BookLog>{
        val localDate = LocalDate.now()
        val bookLogList = mutableListOf<BookLog>()

        bookLogRepository.getAll().forEach {
            if (it.returnDate == null
                && it.issueDate
                    .toLocalDate()
                    .plusDays(returnPeriod1.toLong())
                    .isBefore(localDate)
            ) {
                bookLogList.add(it)
                emailService.sendEmail(it)
            }
        }
        return bookLogList
    }

}