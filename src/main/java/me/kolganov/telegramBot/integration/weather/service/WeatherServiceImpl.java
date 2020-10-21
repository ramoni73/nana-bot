package me.kolganov.telegramBot.integration.weather.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kolganov.telegramBot.config.Settings;
import me.kolganov.telegramBot.utils.Constants;
import me.kolganov.telegramBot.integration.weather.domain.WeatherMain;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final Settings settings;

    @Override
    public WeatherMain getWeatherInfo(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = settings.getWeatherApiBaseUrl() + "&q=" + city + "&appid=" + settings.getWeatherApiKey();

        WeatherMain weather;
        try {
            weather = restTemplate.getForObject(url, WeatherMain.class);
        } catch (Exception e) {
            weather = new WeatherMain();
            weather.setError(Constants.WEATHER_OUTPUT_ERROR);
        }

        log.info("weather: " + weather);
        return weather;
    }
}
