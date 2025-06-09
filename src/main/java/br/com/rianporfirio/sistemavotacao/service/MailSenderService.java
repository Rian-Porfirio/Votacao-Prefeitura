package br.com.rianporfirio.sistemavotacao.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    private final JavaMailSender mailSender;

    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("prefeitura.votacao.vale@gmail.com");
        message.setSubject("Teste");
        message.setText(text);
        message.setTo(to);

        mailSender.send(message);
    }

}
