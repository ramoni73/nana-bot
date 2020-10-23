package me.kolganov.telegramBot.telegram.service;

import lombok.RequiredArgsConstructor;
import me.kolganov.telegramBot.telegram.domain.TwitchStreamersSubscription;
import me.kolganov.telegramBot.telegram.repository.TwitchStreamersSubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwitchStreamersSubscriptionServiceImpl implements TwitchStreamersSubscriptionService {
    private final TwitchStreamersSubscriptionRepository twitchStreamersSubscriptionRepository;

    @Override
    public TwitchStreamersSubscription saveSubscription(TwitchStreamersSubscription subscription) {
        return twitchStreamersSubscriptionRepository.save(subscription);
    }

    @Override
    public void deleteSubscription(TwitchStreamersSubscription subscription) {
        twitchStreamersSubscriptionRepository.delete(subscription);
    }
}
