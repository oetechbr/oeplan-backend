package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.dto.InviteMailRequest;
import br.tech.oe.plan.service.MailService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    public MailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendUserInvite(InviteMailRequest invite) {
        Context context = new Context();
        context.setVariable("user", invite.getUser());
        context.setVariable("inviteUrl", invite.getInviteUrl());

        String processHtml = templateEngine.process("invite-email", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            helper.setText(processHtml, true);
            helper.setTo(invite.getTo());
            helper.setSubject(invite.getTitle());
            helper.setFrom("noreply@theproject.id");
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
