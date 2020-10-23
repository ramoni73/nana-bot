package me.kolganov.telegramBot.telegram.botService.twitch;

import lombok.RequiredArgsConstructor;
import me.kolganov.telegramBot.integration.twitch.domain.channel.ChannelData;
import me.kolganov.telegramBot.integration.twitch.domain.channel.Channel;
import me.kolganov.telegramBot.integration.twitch.domain.game.GameData;
import me.kolganov.telegramBot.integration.twitch.service.TwitchService;
import me.kolganov.telegramBot.telegram.botService.message.MessagePresenter;
import me.kolganov.telegramBot.telegram.domain.TelegramUser;
import me.kolganov.telegramBot.telegram.domain.TwitchStreamersSubscription;
import me.kolganov.telegramBot.telegram.service.TelegramUserService;
import me.kolganov.telegramBot.telegram.service.TwitchStreamersSubscriptionService;
import me.kolganov.telegramBot.utils.Constants;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramTwitchServiceImpl implements TelegramTwitchService {
    private final TwitchService twitchService;
    private final TelegramUserService telegramUserService;
    private final TwitchStreamersSubscriptionService twitchStreamersSubscriptionService;
    private final MessagePresenter messagePresenter;

    @Override
    public SendMessage sendMessage(Message message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(Constants.TWITCH_INPUT_CHECK_SUBSCRIPTIONS));
        row1.add(new KeyboardButton(Constants.MENU_INPUT));

        keyboard.add(row1);

        replyKeyboardMarkup.setKeyboard(keyboard);

        return messagePresenter.presentMessageWithMarkup(
                        message.getChatId(),
                        Constants.TWITCH_OUTPUT_ENTER_STREAMER_NAME,
                        replyKeyboardMarkup
                );
    }

    @Override
    public SendMessage sendStreamerInfo(Message message) {
        String inputMessage = message.getText().replace("/", "");
        Channel channel = twitchService.getChanel(inputMessage);

        TelegramUser telegramUser = telegramUserService.getUser(message.getFrom().getId(), message.getChatId());

        if (null != channel.getError()) {
            return messagePresenter.presentTextMessage(message.getChatId(), channel.getError());
        } else {
            ChannelData channelData = channel.getChannelDataList()
                    .stream()
                    .filter(c -> inputMessage.equalsIgnoreCase(c.getDisplayName()))
                    .findFirst()
                    .orElse(new ChannelData(Constants.TWITCH_OUTPUT_STREAMER_NOT_FOUND));

            if (channelData.isLive()) {
                GameData gameData = twitchService.getGame(channelData.getGameId()).getGameDataList()
                        .stream()
                        .findFirst()
                        .orElse(new GameData());

                return messagePresenter.presentMessageWithMarkup(
                        message.getChatId(),
                        onlineStreamerInfoParser(channelData, gameData),
                        getKeyboard(inputMessage, telegramUser, channelData));
            } else {
                return messagePresenter.presentMessageWithMarkup(
                        message.getChatId(),
                        offlineStreamerInfoParser(channelData),
                        getKeyboard(inputMessage, telegramUser, channelData));
            }
        }
    }

    @Override
    public SendMessage sendSubscriptionMessage(CallbackQuery callbackQuery) {
        TelegramUser telegramUser = telegramUserService.getUser(
                callbackQuery.getFrom().getId(),
                callbackQuery.getMessage().getChatId());
        telegramUser.setUsername(callbackQuery.getFrom().getUserName());

        SendMessage message;

        if (telegramUser.getTwitchStreamersSubscriptions()
                .stream()
                .noneMatch(s -> s.getStreamer().equalsIgnoreCase(callbackQuery.getData()))) {

            TwitchStreamersSubscription twitchStreamersSubscription = TwitchStreamersSubscription
                    .builder()
                    .streamer(callbackQuery.getData().toLowerCase())
                    .telegramUser(telegramUser)
                    .build();

            telegramUserService.saveUser(telegramUser);
            twitchStreamersSubscriptionService.saveSubscription(twitchStreamersSubscription);

            ChannelData channelData = twitchService.getChanel(callbackQuery.getData().toLowerCase())
                    .getChannelDataList()
                    .stream()
                    .findFirst()
                    .orElse(new ChannelData());

            twitchService.subscribe(channelData.getId());

            message = messagePresenter.presentTextMessage(
                    callbackQuery.getMessage().getChatId(),
                    Constants.TWITCH_OUTPUT_SUBSCRIBE_SUCCESS);
        } else {
            TwitchStreamersSubscription subscription = telegramUser.getTwitchStreamersSubscriptions()
                    .stream()
                    .filter(s -> s.getStreamer().equalsIgnoreCase(callbackQuery.getData()))
                    .findFirst()
                    .orElse(TwitchStreamersSubscription.builder().build());

            twitchStreamersSubscriptionService.deleteSubscription(subscription);

            message = messagePresenter.presentTextMessage(
                    callbackQuery.getMessage().getChatId(),
                    Constants.TWITCH_OUTPUT_UNSUBSCRIBE_SUCCESS);
        }
        return message;
    }

    @Override
    public SendMessage sendOnlineStreamers(Message message) {
        TelegramUser telegramUser = telegramUserService.getUser(message.getFrom().getId(), message.getChatId());

        if (telegramUser.getTwitchStreamersSubscriptions().isEmpty()) {
            return messagePresenter.presentTextMessage(
                    message.getChatId(),
                    Constants.TWITCH_OUTPUT_EMPTY_SUBSCRIPTIONS);
        } else {
            StringBuilder replyText = new StringBuilder();
            telegramUser.getTwitchStreamersSubscriptions()
                    .stream()
                    .sorted()
                    .forEach(s -> {
                        Channel channel = twitchService.getChanel(s.getStreamer());
                        ChannelData channelData = channel.getChannelDataList()
                                .stream()
                                .filter(c -> s.getStreamer().equalsIgnoreCase(c.getDisplayName()))
                                .findFirst()
                                .orElse(new ChannelData(Constants.TWITCH_OUTPUT_STREAMER_NOT_FOUND));

                        if (channelData.isLive()) {
                            replyText.append("/").append(s.getStreamer()).append(" - Online").append("\n");
                        } else {
                            replyText.append("/").append(s.getStreamer()).append("\n");
                        }
                    });

            return messagePresenter.presentTextMessage(
                    message.getChatId(),
                    replyText.toString());
        }
    }

    private ReplyKeyboard getKeyboard(String inputText, TelegramUser telegramUser, ChannelData channelData) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();

        if (telegramUser.getTwitchStreamersSubscriptions()
                .stream()
                .anyMatch(s -> s.getStreamer().equalsIgnoreCase(inputText)))
            inlineKeyboardButton.setText(Constants.TWITCH_OUTPUT_UNSUBSCRIBE);
        else
            inlineKeyboardButton.setText(Constants.TWITCH_OUTPUT_SUBSCRIBE);

        inlineKeyboardButton.setCallbackData(channelData.getDisplayName());

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(inlineKeyboardButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
