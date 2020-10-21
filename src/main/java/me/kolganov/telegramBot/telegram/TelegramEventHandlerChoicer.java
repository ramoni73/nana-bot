package me.kolganov.telegramBot.telegram;

import me.kolganov.telegramBot.telegram.handler.TelegramCallbackEventHandler;
import me.kolganov.telegramBot.telegram.handler.TelegramEventHandler;
import me.kolganov.telegramBot.telegram.state.BotState;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TelegramEventHandlerChoicer {
    private final Map<BotState, TelegramEventHandler> eventHandlerMap = new HashMap<>();
    private final Map<BotState, TelegramCallbackEventHandler> callbackEventHandlerMap = new HashMap<>();

    public TelegramEventHandlerChoicer(
            List<TelegramEventHandler> eventHandlers,
            List<TelegramCallbackEventHandler> callbackEventHandlers) {
        eventHandlers.forEach(handler -> this.eventHandlerMap.put(handler.getHandlerName(), handler));
        callbackEventHandlers.forEach(handler -> this.callbackEventHandlerMap.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState botState, Message message) {
        TelegramEventHandler telegramEventHandler = getEventHandler(botState);
        return telegramEventHandler.handleMessage(message);
    }

    public SendMessage processCallbackMessage(BotState botState, CallbackQuery callbackQuery) {
        TelegramCallbackEventHandler telegramCallbackEventHandler = getCallbackEventHandler(botState);
        return telegramCallbackEventHandler.handleMessage(callbackQuery);
    }

    private TelegramEventHandler getEventHandler(BotState botState) {
        if (botState.equals(BotState.START))
            return eventHandlerMap.get(BotState.START);

        if (botState.equals(BotState.WEATHER))
            return eventHandlerMap.get(BotState.WEATHER);

        if (botState.equals(BotState.TWITCH))
            return eventHandlerMap.get(BotState.TWITCH);

        return eventHandlerMap.get(botState);
    }

    private TelegramCallbackEventHandler getCallbackEventHandler(BotState botState) {
        if (botState.equals(BotState.TWITCH))
            return callbackEventHandlerMap.get(BotState.TWITCH);

        return callbackEventHandlerMap.get(botState);
    }
}
