package me.kolganov.telegramBot.integration.weather.service;

import me.kolganov.telegramBot.integration.weather.domain.WeatherMain;

public interface WeatherService {
    /**
     * Get weather info from open weather api by city name
     * @param city - city name
     * @return - WeatherMain
     */
    WeatherMain getWeatherInfo(String city);
}
