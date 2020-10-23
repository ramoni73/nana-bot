package me.kolganov.telegramBot.telegram.botService.menu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TelegramMenuService {
    SendMessage getMenu(long chatId);
}
