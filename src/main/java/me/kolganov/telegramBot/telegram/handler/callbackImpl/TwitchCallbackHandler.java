package me.kolganov.telegramBot.telegram.handler.callbackImpl;

import lombok.RequiredArgsConstructor;
import me.kolganov.telegramBot.telegram.handler.TelegramCallbackEventHandler;
import me.kolganov.telegramBot.telegram.botService.twitch.TelegramTwitchService;
import me.kolganov.telegramBot.telegram.state.BotState;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
@RequiredArgsConstructor
public class TwitchCallbackHandler implements TelegramCallbackEventHandler {
    private final TelegramTwitchService telegramTwitchService;

    @Override
    public SendMessage handleMessage(CallbackQuery callbackQuery) {
        return telegramTwitchService.sendSubscriptionMessage(callbackQuery);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.TWITCH;
    }
}
