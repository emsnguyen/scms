package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.dto.NotificationEmail;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(new InternetAddress("quynhpthe130472@fpt.edu.vn"));
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Request email sent");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new AppException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }

}