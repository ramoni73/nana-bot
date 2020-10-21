package me.kolganov.telegramBot.telegram.service.twitch;

import me.kolganov.telegramBot.emoji.Emojis;
import me.kolganov.telegramBot.integration.twitch.domain.ChannelData;
import me.kolganov.telegramBot.integration.twitch.domain.GameData;
import me.kolganov.telegramBot.utils.Constants;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public interface TelegramTwitchService {
    SendMessage sendMessage(Message message);
    SendMessage sendStreamerInfo(Message message);
    SendMessage sendSubscriptionMessage(CallbackQuery callbackQuery);
    SendMessage sendOnlineStreamers(Message message);

    default String onlineStreamerInfoParser(ChannelData channelData, GameData gameData) {
        if (null != channelData.getError())
            return channelData.getError();

        StringBuilder stringBuilder = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(channelData.getStartedAt(), formatter);

        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Europe/Moscow"));
        String time = zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "(МСК)";

        stringBuilder
                .append(Emojis.FACE_WITH_MONOCLE).append(" Стример: ").append(channelData.getDisplayName()).append("\n")
                .append(Emojis.VIDEO_GAME).append(" Играет в: ").append(gameData.getName()).append("\n")
                .append(Emojis.LABEL).append(" Описание: ").append(channelData.getTitle()).append("\n")
                .append(Emojis.ALARM_CLOCK).append(" Начало трансляции: ").append(time).append("\n").append("\n")
                .append("https://www.twitch.tv/").append(channelData.getDisplayName());

        return stringBuilder.toString();
    }

    default String offlineStreamerInfoParser(ChannelData channelData) {
        if (null != channelData.getError())
            return channelData.getError();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(Emojis.FACE_WITH_MONOCLE).append(" Стример: ").append(channelData.getDisplayName()).append("\n")
                .append(Emojis.VIDEO_GAME).append(Constants.TWITCH_OUTPUT_STREAMER_OFFLINE).append("\n").append("\n")
                .append("https://www.twitch.tv/").append(channelData.getDisplayName());

        return stringBuilder.toString();
    }

    default String streamerInfoAnotherParser(ChannelData channelData) {
        if (null != channelData.getError())
            return channelData.getError();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append(Emojis.FACE_WITH_MONOCLE).append(" Стример: ").append(channelData.getDisplayName()).append("\n");

        return stringBuilder.toString();
    }
}
