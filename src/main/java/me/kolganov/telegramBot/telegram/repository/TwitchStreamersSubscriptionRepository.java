package me.kolganov.telegramBot.telegram.repository;

import me.kolganov.telegramBot.telegram.domain.TwitchStreamersSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitchStreamersSubscriptionRepository extends JpaRepository<TwitchStreamersSubscription, Long> {
}
