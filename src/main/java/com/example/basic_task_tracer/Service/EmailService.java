package com.example.basic_task_tracer.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Async
    public void sendMailafterCreatedTask(String to, String taskTitle, String taskDescription, Boolean completed){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Новая задача была создана: " + taskTitle);
        String completedText = (completed != null && completed) ? "Да" : "Нет";

        mailMessage.setText(
                "Задача: " + taskTitle + "\n" +
                        "Описание: " + taskDescription + "\n" +
                        "Закончена?: " + completedText
        );
        mailSender.send(mailMessage);
    }
}
