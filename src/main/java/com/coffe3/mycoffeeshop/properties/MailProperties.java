package com.coffe3.mycoffeeshop.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("mail")
public class MailProperties {

    private String mailHost;
    private Integer mailPort;
    private String mailUserName;
    private String mailPassword;
    private String recipientEmail;

}
