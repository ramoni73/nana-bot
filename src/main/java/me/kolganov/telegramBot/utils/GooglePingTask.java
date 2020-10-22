package me.kolganov.telegramBot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GooglePingTask {

    @Scheduled(fixedRate = 1500000)
    public void ping() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.google.com/";

        log.info("ping: " + url);
        restTemplate.getForObject(url, String.class);
    }
}
