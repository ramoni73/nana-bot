package me.kolganov.telegramBot.telegram.service.weather;

import lombok.RequiredArgsConstructor;
import me.kolganov.telegramBot.integration.weather.domain.WeatherMain;
import me.kolganov.telegramBot.integration.weather.service.WeatherService;
import me.kolganov.telegramBot.telegram.service.message.MessagePresenter;
import me.kolganov.telegramBot.utils.Constants;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class TelegramWeatherServiceImpl implements TelegramWeatherService {
    private final WeatherService weatherService;
    private final MessagePresenter messagePresenter;

    @Override
    public SendMessage sendMessage(Message message) {
        return messagePresenter.presentTextMessage(
                message.getChatId(),
                Constants.WEATHER_OUTPUT_ENTER_CITY);
    }

    @Override
    public SendMessage sendWeather(Message message) {
        WeatherMain weather = weatherService.getWeatherInfo(message.getText());

        if (null != weather.getError())
            return messagePresenter.presentTextMessage(message.getChatId(), weather.getError());
        else
            return messagePresenter.presentTextMessage(message.getChatId(), weatherParser(weather));
    }
}
