package com.scms.supplychainmanagementsystem.util;

import com.scms.supplychainmanagementsystem.dto.ForgotPasswordEmail;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMailForgotPassword(ForgotPasswordEmail forgotPasswordEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(forgotPasswordEmail.getRecipient());
            messageHelper.setSubject(forgotPasswordEmail.getSubject());
            messageHelper.setText(mailContentBuilder.buildMailForgotPassword(forgotPasswordEmail.getUsername(), forgotPasswordEmail.getContent()), true);
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Request reset password email sent");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new AppException("Exception occurred when sending mail to " + forgotPasswordEmail.getRecipient(), e);
        }
    }

}
