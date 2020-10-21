package me.kolganov.telegramBot.integration.weather.service;

import me.kolganov.telegramBot.integration.weather.domain.WeatherMain;

public interface WeatherService {
    WeatherMain getWeatherInfo(String city);
}
