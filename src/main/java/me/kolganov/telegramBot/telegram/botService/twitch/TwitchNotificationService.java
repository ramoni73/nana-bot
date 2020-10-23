package me.kolganov.telegramBot.telegram.botService.twitch;

import me.kolganov.telegramBot.emoji.Emojis;
import me.kolganov.telegramBot.integration.twitch.domain.game.GameData;
import me.kolganov.telegramBot.integration.twitch.domain.stream.StreamData;
import me.kolganov.telegramBot.integration.twitch.domain.stream.Stream;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface TwitchNotificationService {
    List<SendMessage> sendMessageToSubs(Stream stream);

    default String onlineStreamerInfoParser(StreamData streamData, GameData gameData) {
        StringBuilder stringBuilder = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(streamData.getStartedAt(), formatter);

        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Europe/Moscow"));
        zonedDateTime = zonedDateTime.plusHours(3);
        String time = zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "(МСК)";

        stringBuilder
                .append(Emojis.FACE_WITH_MONOCLE).append(" Стример: ").append(streamData.getUserName()).append("\n")
                .append(Emojis.VIDEO_GAME).append(" Играет в: ").append(gameData.getName()).append("\n")
                .append(Emojis.LABEL).append(" Описание: ").append(streamData.getTitle()).append("\n")
                .append(Emojis.ALARM_CLOCK).append(" Начало трансляции: ").append(time).append("\n").append("\n")
                .append("https://www.twitch.tv/").append(streamData.getUserName());

        return stringBuilder.toString();
    }
}
