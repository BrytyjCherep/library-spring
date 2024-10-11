package com.example.library.service

import com.example.library.model.BookLog
import jakarta.mail.internet.InternetAddress
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val javaMailSender: JavaMailSender,
    @Value("\${spring.mail.sender.email}") private val senderEmail: String,
    @Value("\${spring.mail.sender.text}") private val senderText: String
) {
    fun sendEmail(bookLog: BookLog){
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message)
        helper.setFrom(InternetAddress(senderEmail, senderText))
        helper.setTo(bookLog.reader.email)
        helper.setSubject("Истек срок возврата книги")
        helper.setText("<p>${bookLog.reader.name} ваш период для возврата книги истек. Просим в ближайшее время вернуть книгу ${bookLog.libraryBook.book.name} в библиотеку</p>", true)
        javaMailSender.send(message)
    }
}