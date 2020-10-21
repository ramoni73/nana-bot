package me.kolganov.telegramBot.integration.weather.domain;

import lombok.Data;

@Data
public class Main {
    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private double pressure;
    private double humidity;
}
