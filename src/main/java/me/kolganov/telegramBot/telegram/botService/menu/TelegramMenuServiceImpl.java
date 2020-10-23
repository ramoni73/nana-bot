package me.kolganov.telegramBot.telegram.botService.menu;

import lombok.RequiredArgsConstructor;
import me.kolganov.telegramBot.telegram.botService.message.MessagePresenter;
import me.kolganov.telegramBot.utils.Constants;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramMenuServiceImpl implements TelegramMenuService {
    private final MessagePresenter messagePresenter;

    @Override
    public SendMessage getMenu(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(Constants.WEATHER_INPUT_ASK));
        row1.add(new KeyboardButton(Constants.MENU_INPUT));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(Constants.TWITCH_INPUT_ASK));

        keyboard.add(row1);
        keyboard.add(row2);

        replyKeyboardMarkup.setKeyboard(keyboard);

        return messagePresenter.presentMessageWithMarkup(
                chatId,
                Constants.MENU_OUTPUT,
                replyKeyboardMarkup
        );
    }
}
