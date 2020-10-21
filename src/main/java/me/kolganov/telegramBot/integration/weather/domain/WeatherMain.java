package me.kolganov.telegramBot.integration.weather.domain;

import lombok.Data;

import java.util.List;

@Data
public class WeatherMain {
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private String error;
}
