package me.kolganov.telegramBot.telegram.service;

import me.kolganov.telegramBot.telegram.domain.TelegramUser;

import java.util.List;

public interface TelegramUserService {
    TelegramUser getUser(Integer userId, Long chatId);
    TelegramUser saveUser(TelegramUser telegramUser);
    List<TelegramUser> getAll();
}
