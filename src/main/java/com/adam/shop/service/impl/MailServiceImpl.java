package com.adam.shop.service.impl;

import com.adam.shop.domain.dao.Template;
import com.adam.shop.service.MailService;
import com.adam.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final TemplateService templateService;
    private final JavaMailSender javaMailSender;
    private final ITemplateEngine iTemplateEngine;

    @Async //wywołanie metody w osobnym wątku i nieczekanie na odpowiedź
    @Override
    public void send(String mail, String templateName, Map<String, Object> variables, byte[] file, String fileName) {
        Template template = templateService.findByName(templateName);
        String body = iTemplateEngine.process(template.getBody(), new Context(Locale.getDefault(), variables));
        javaMailSender.send(mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.addTo(mail);
            helper.setSubject(template.getSubject());
            helper.setText(body, true);
            if (file != null && fileName != null) {
                helper.addAttachment(fileName, new ByteArrayResource(file));
            }
        });
    }
}
