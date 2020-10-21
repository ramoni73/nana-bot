package me.kolganov.telegramBot.telegram.handler.messageImpl;

import lombok.RequiredArgsConstructor;
import me.kolganov.telegramBot.telegram.handler.TelegramEventHandler;
import me.kolganov.telegramBot.telegram.service.menu.TelegramMenuService;
import me.kolganov.telegramBot.telegram.state.BotState;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class StartEventHandler implements TelegramEventHandler {
    private final TelegramMenuService telegramMenuService;

    @Override
    public SendMessage handleMessage(Message message) {
        return telegramMenuService.getMenu(message.getChatId());
    }

    @Override
    public BotState getHandlerName() {
        return BotState.START;
    }
}
