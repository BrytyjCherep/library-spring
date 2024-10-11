package com.example.library.service

import com.example.library.model.BookLog
import com.example.library.repository.BookLogRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SchedulerService(
    private val bookLogRepository: BookLogRepository,
    private val emailService: EmailService
) {

    @Value("\${app.returnPeriod:14}")
    lateinit var returnPeriod: String

    @Scheduled(cron = "* 16 * * * *")
    fun getListOfOverdueReaders() : List<BookLog>{

        val localDate = LocalDate.now()
        val bookLogList = bookLogRepository.getListOfOverdueReaders(localDate.minusDays(returnPeriod.toLong()))

        bookLogList.forEach {
            emailService.sendEmail(it)
        }
        return bookLogList
    }

}