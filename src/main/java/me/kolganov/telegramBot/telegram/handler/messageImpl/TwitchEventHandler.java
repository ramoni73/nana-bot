package me.kolganov.telegramBot.telegram.handler.messageImpl;

import lombok.RequiredArgsConstructor;
import me.kolganov.telegramBot.telegram.handler.TelegramEventHandler;
import me.kolganov.telegramBot.telegram.service.twitch.TelegramTwitchService;
import me.kolganov.telegramBot.telegram.state.BotState;
import me.kolganov.telegramBot.utils.Constants;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class TwitchEventHandler implements TelegramEventHandler {
    private final TelegramTwitchService telegramTwitchService;

    @Override
    public SendMessage handleMessage(Message message) {
        if (Constants.TWITCH_INPUT_ASK.equalsIgnoreCase(message.getText()))
            return telegramTwitchService.sendMessage(message);

        if (Constants.TWITCH_INPUT_CHECK_SUBSCRIPTIONS.equalsIgnoreCase(message.getText()))
            return telegramTwitchService.sendOnlineStreamers(message);

        return telegramTwitchService.sendStreamerInfo(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.TWITCH;
    }
}
