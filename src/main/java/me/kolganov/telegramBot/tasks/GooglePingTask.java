package me.kolganov.telegramBot.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GooglePingTask {

    @Scheduled(fixedRate = 1200000)
    public void ping() {
        log.info("---Ping task start---");
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.google.com/";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        log.info("ping: {}, response_code: {}", url, responseEntity.getStatusCode());
        log.info("---Ping task end---");
    }
}
