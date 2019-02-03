package com.coffe3.mycoffeeshop.config;

import com.coffe3.mycoffeeshop.properties.MailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@SuppressWarnings("unused")
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MailConfig {

    private final MailProperties mailProperties;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getMailHost());
        mailSender.setPort(mailProperties.getMailPort());

        mailSender.setUsername(mailProperties.getMailUserName());
        mailSender.setPassword(mailProperties.getMailPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
