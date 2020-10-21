package me.kolganov.telegramBot.telegram.repository;

import me.kolganov.telegramBot.telegram.domain.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
    Optional<TelegramUser> findByUserIdAndChatId(Integer userId, Long chatId);
}
