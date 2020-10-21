package me.kolganov.telegramBot.telegram;

import lombok.RequiredArgsConstructor;
import me.kolganov.telegramBot.config.Settings;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramWebhookBot {
    private final Settings settings;
    private final TelegramEventProcessor telegramEventProcessor;

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramEventProcessor.processInputMessage(update);
    }

    @Override
    public String getBotUsername() {
        return settings.getTelegramBotUsername();
    }

    @Override
    public String getBotToken() {
        return settings.getTelegramBotToken();
    }

    @Override
    public String getBotPath() {
        return settings.getTelegramBotPath();
    }
}
