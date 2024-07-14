package com.example.comic_store.service.impl;

import com.example.comic_store.dto.MailDTO;
import com.example.comic_store.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Override
    @Async
    public void sendMail(MailDTO mailDTO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailDTO.getSubject());
        simpleMailMessage.setText(mailDTO.getMessage());
        simpleMailMessage.setTo(mailDTO.getMail());
        mailSender.send(simpleMailMessage);
    }
}
