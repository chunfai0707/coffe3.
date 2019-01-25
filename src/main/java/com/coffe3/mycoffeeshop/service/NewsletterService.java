package com.coffe3.mycoffeeshop.service;

import com.coffe3.mycoffeeshop.domain.Newsletter;
import com.coffe3.mycoffeeshop.repository.NewsletterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NewsletterService {

    private final NewsletterRepository newsletterRepository;

    public void subscribeNewsletter(Newsletter newsletter) {

        newsletter.setNewsletterCreatedAt(new Date());
        newsletter.setNewsletterLastUpdated(new Date());

        newsletterRepository.save(newsletter);
    }
}
