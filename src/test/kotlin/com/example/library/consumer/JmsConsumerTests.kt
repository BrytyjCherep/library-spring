package com.example.library.consumer

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JmsConsumerTests {
    @Autowired
    lateinit var jmsConsumer: JmsConsumer

    @Test
    fun parseXML() {
        val isbn = "0123456789"
        val readerId = 11
        val message =
            """<readerBooks>
                    <book>
                        <isbn>${isbn}</isbn>
                    </book>
                    <reader>
                        <id>${readerId}</id>
                    </reader>
                    </readerBooks>"""
        assert(Pair(isbn, readerId) == jmsConsumer.parseXML(message))
    }

    @Test
    fun parseXML1() {
        val isbn = ""
        val readerId = 11
        val message =
            """<readerBooks>
                    <book>
                        <isbn>${isbn}</isbn>
                    </book>
                    <reader>
                        <id>${readerId}</id>
                    </reader> 
                    </readerBooks>"""
        assert(jmsConsumer.parseXML(message) == null)
    }

    @Test
    fun parseXML2() {
        val isbn = "0123456789"
        val readerId = null
        val message =
            """<readerBooks>
                    <book>
                        <isbn>${isbn}</isbn>
                    </book>
                    <reader>
                        <id>${readerId}</id>
                    </reader> 
                    </readerBooks>"""
        assert(jmsConsumer.parseXML(message) == null)
    }

}