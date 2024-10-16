package com.example.library.consumer

import com.example.library.repository.BookLogRepository
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import org.w3c.dom.Document
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.IOException
import java.io.StringReader
import java.time.LocalDate
import javax.xml.parsers.DocumentBuilderFactory


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
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val result = InputSource(StringReader(message))
        var xmlDoc: Document? = null

        try {
            xmlDoc = builder.parse(result)
        } catch (e: SAXException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

        logger.info("Start parsing message: ${message}")

        val isbn = xmlDoc.documentElement.getElementsByTagName("isbn").item(0).textContent
        val readerId = xmlDoc.documentElement.getElementsByTagName("id").item(0).textContent.toIntOrNull()

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
        logger.info("Ended parsing message")

        return Pair(isbn, readerId)
    }


}