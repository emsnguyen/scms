package com.scms.supplychainmanagementsystem.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    public String buildMailForgotPassword(String username, String content) {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("content", content);
        return templateEngine.process("mailForgotPassword", context);
    }
}
