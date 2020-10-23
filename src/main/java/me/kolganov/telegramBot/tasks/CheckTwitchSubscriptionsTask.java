package me.kolganov.telegramBot.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kolganov.telegramBot.integration.twitch.domain.subscription.Subscription;
import me.kolganov.telegramBot.integration.twitch.domain.subscription.SubscriptionData;
import me.kolganov.telegramBot.integration.twitch.service.TwitchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckTwitchSubscriptionsTask {
    private final TwitchService twitchService;

    @Scheduled(cron = "0 07 09 ? * *")
    public void check() {
        log.info("---Twitch subscriptions check task start---");

        Subscription subscription = twitchService.checkSubscriptions();
        List<SubscriptionData> updateList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime now = LocalDateTime.now();

        subscription.getSubscriptionData().forEach(s -> {
            LocalDateTime dateTime = LocalDateTime.parse(s.getExpiresAt(), formatter);
            if (now.plusDays(1).equals(dateTime))
                updateList.add(s);
        });

        updateList.forEach(this::refreshSubscriptions);

        log.info("---Twitch subscriptions check task end---");
    }

    private void refreshSubscriptions(SubscriptionData subscriptionData) {
        URI uri = URI.create(subscriptionData.getTopic());
        String userId = uri.getQuery().replace("user_id=", "");
        twitchService.subscribe(userId);
    }
}
