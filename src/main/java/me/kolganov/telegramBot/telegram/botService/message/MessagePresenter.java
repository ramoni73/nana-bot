package me.kolganov.telegramBot.telegram.botService.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface MessagePresenter {
    SendMessage presentMessageWithMarkup(long chatId, String text, ReplyKeyboard markup);
    SendMessage presentTextMessage(long chatId, String text);
}
