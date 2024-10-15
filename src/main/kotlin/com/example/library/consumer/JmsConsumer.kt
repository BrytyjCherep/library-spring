package com.example.library.consumer

import com.example.library.repository.BookLogRepository
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class JmsConsumer(private val bookLogRepository: BookLogRepository) {
    val logger = LoggerFactory.getLogger(JmsConsumer::class.java)

    @JmsListener(destination = "library-queue")
    fun receiveMessage(message: String) {
        val pair = parseXML(message) ?: return
        val isbn = pair.first
        val readerId = pair.second
        logger.info("xml read correctly")
        val listOfBookLog = bookLogRepository.getListForReturnBook(isbn, readerId)
        if (listOfBookLog.isEmpty()) {
            logger.info("BookLog already returned book or don't exists. Or reader with this id don't exist. Or book with this isbn don't exist")
            return
        }
        logger.info("correctly get list of book log")
        bookLogRepository.updateReturnDate(listOfBookLog[0].id!!, LocalDate.now())
        logger.info("return date update successfully")
    }

    fun parseXML(message: String): Pair<String, Int>? {
        val linesList = message.lines()
        val isbn = linesList[2].substringAfter('>').substringBeforeLast('<')
        val readerId = linesList[5].substringAfter('>').substringBeforeLast('<').toIntOrNull()
        if (isbn.isEmpty()) {
            logger.info("ISBN is empty")
            return null
        }
        logger.info("isbn read correctly from xml")
        if (readerId == null) {
            logger.info("Wrong reader id format or null")
            return null
        }
        logger.info("reader id read correctly from xml")

        return Pair(isbn, readerId)
    }


}