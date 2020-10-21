package me.kolganov.telegramBot.telegram.handler;

import me.kolganov.telegramBot.telegram.state.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface TelegramCallbackEventHandler {
    SendMessage handleMessage(CallbackQuery callbackQuery);
    BotState getHandlerName();
}
