package com.example.otp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender sender;

    public void sendEmail(String email,String otp){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("otp code");
        message.setText("your otp code "+otp);
        sender.send(message);
    }
}
