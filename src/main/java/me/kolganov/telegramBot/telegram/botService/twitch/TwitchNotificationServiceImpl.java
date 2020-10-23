package me.kolganov.telegramBot.telegram.botService.twitch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kolganov.telegramBot.integration.twitch.domain.game.GameData;
import me.kolganov.telegramBot.integration.twitch.domain.stream.StreamData;
import me.kolganov.telegramBot.integration.twitch.domain.stream.Stream;
import me.kolganov.telegramBot.integration.twitch.service.TwitchService;
import me.kolganov.telegramBot.telegram.botService.message.MessagePresenter;
import me.kolganov.telegramBot.telegram.domain.TelegramUser;
import me.kolganov.telegramBot.telegram.service.TelegramUserService;
import me.kolganov.telegramBot.utils.Constants;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwitchNotificationServiceImpl implements TwitchNotificationService {
    private final TwitchService twitchService;
    private final TelegramUserService telegramUserService;
    private final MessagePresenter messagePresenter;

    @Override
    public List<SendMessage> sendMessageToSubs(Stream stream) {
        List<TelegramUser> telegramUsers = telegramUserService.getAll();
        StreamData streamData = stream.getStreamData()
                .stream()
                .findFirst()
                .orElse(new StreamData());

        List<SendMessage> messages = new ArrayList<>();

        if ("live".equalsIgnoreCase(streamData.getType())) {
            GameData gameData = twitchService.getGame(streamData.getGameId()).getGameDataList()
                    .stream()
                    .findFirst()
                    .orElse(new GameData());

            List<TelegramUser> telegramUsersToNotify = telegramUsers
                    .stream()
                    .filter(u -> isSub(u, streamData.getUserName()))
                    .collect(Collectors.toList());

            telegramUsersToNotify.forEach(u -> log.info("User to notify: " + u));

            telegramUsersToNotify.forEach(u -> messages.add(messagePresenter.presentMessageWithMarkup(
                    u.getChatId(),
                    onlineStreamerInfoParser(streamData, gameData),
                    getKeyboard(u, streamData))));
        }
        return messages;
    }

    private boolean isSub(TelegramUser telegramUser, String streamerName) {
        return telegramUser.getTwitchStreamersSubscriptions()
                .stream()
                .anyMatch(s -> s.getStreamer().equalsIgnoreCase(streamerName));
    }

    private ReplyKeyboard getKeyboard(TelegramUser telegramUser, StreamData streamData) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();

        if (telegramUser.getTwitchStreamersSubscriptions()
                .stream()
                .anyMatch(s -> s.getStreamer().equalsIgnoreCase(streamData.getUserName())))
            inlineKeyboardButton.setText(Constants.TWITCH_OUTPUT_UNSUBSCRIBE);
        else
            inlineKeyboardButton.setText(Constants.TWITCH_OUTPUT_SUBSCRIBE);

        inlineKeyboardButton.setCallbackData(streamData.getUserName());

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(inlineKeyboardButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
