package me.kolganov.telegramBot.telegram.service.weather;

import me.kolganov.telegramBot.emoji.Emojis;
import me.kolganov.telegramBot.integration.weather.domain.WeatherMain;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface TelegramWeatherService {
    SendMessage sendMessage(Message message);
    SendMessage sendWeather(Message message);

    default String weatherParser(WeatherMain weather) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(Emojis.THERMOMETER).append(" Температура: ").append(weather.getMain().getTemp()).append(" C").append("\n")
                .append(Emojis.WALKING).append(" Ощущается как: ").append(weather.getMain().getFeels_like()).append(" C").append("\n")
                .append(Emojis.WHITE_CIRCLE).append(" Давление: ").append(weather.getMain().getPressure()).append(" мм.рт.ст.").append("\n")
                .append(Emojis.DROPLET).append(" Влажность: ").append(weather.getMain().getHumidity()).append("%").append("\n");

        return stringBuilder.toString();
    }
}
