package me.kolganov.telegramBot.telegram.handler.messageImpl;

import lombok.RequiredArgsConstructor;
import me.kolganov.telegramBot.telegram.handler.TelegramEventHandler;
import me.kolganov.telegramBot.telegram.botService.weather.TelegramWeatherService;
import me.kolganov.telegramBot.telegram.state.BotState;
import me.kolganov.telegramBot.utils.Constants;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class WeatherEventHandler implements TelegramEventHandler {
    private final TelegramWeatherService telegramWeatherService;

    @Override
    public SendMessage handleMessage(Message message) {
        if (Constants.WEATHER_INPUT_ASK.equals(message.getText()))
            return telegramWeatherService.sendMessage(message);

        return telegramWeatherService.sendWeather(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WEATHER;
    }
}
