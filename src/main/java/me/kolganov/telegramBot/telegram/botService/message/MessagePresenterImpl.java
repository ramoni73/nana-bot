package me.kolganov.telegramBot.telegram.botService.message;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Service
public class MessagePresenterImpl implements MessagePresenter {
    @Override
    public SendMessage presentMessageWithMarkup(long chatId, String text, ReplyKeyboard markup) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.enableMarkdown(true);
        message.enableHtml(true);
        message.enableWebPagePreview();
        message.setReplyMarkup(markup);

        return message;
    }

    @Override
    public SendMessage presentTextMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        return message;
    }
}
