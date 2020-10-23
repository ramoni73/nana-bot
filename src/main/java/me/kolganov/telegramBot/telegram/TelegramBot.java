package me.kolganov.telegramBot.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kolganov.telegramBot.config.Settings;
import me.kolganov.telegramBot.integration.twitch.domain.stream.Stream;
import me.kolganov.telegramBot.telegram.botService.twitch.TwitchNotificationService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramWebhookBot {
    private final Settings settings;
    private final TelegramEventProcessor telegramEventProcessor;
    private final TwitchNotificationService twitchNotificationService;

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

    public void sendMessageToTwitchSubs(Stream stream) {
        log.info("Twitch webhook input: " + stream);
        List<SendMessage> messages = twitchNotificationService.sendMessageToSubs(stream);
        for (SendMessage m : messages) {
            try {
                log.info("Twitch webhook output: " + m);
                execute(m);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
