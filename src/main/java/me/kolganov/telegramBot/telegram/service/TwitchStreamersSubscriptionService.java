package me.kolganov.telegramBot.telegram.service;

import me.kolganov.telegramBot.telegram.domain.TwitchStreamersSubscription;

public interface TwitchStreamersSubscriptionService {
    TwitchStreamersSubscription saveSubscription(TwitchStreamersSubscription subscription);
    void deleteSubscription(TwitchStreamersSubscription subscription);
}
