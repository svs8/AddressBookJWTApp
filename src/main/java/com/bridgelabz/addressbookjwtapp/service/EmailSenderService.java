package com.bridgelabz.addressbookjwtapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;



@Component
public class EmailSenderService {

    @Autowired
    private JavaMailSender emailsender;

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("surakshithvittalshetty@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        emailsender.send(message);
        System.out.println("Mail has sent to the User.....!!!!");

    }
}
