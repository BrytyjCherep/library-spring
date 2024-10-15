package com.example.library.producer

import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service

@Service
class JmsProducer(private val jmsTemplate: JmsTemplate) {

    fun sendMessage(isbn: String, readerId: Int) {
        val message =
            "<readerBooks><book><isbn>${isbn}</isbn></book><reader><id>${readerId}</id></reader></readerBooks>"
        jmsTemplate.convertAndSend("library-queue", message)
    }

}