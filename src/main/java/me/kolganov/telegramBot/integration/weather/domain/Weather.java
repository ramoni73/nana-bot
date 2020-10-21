package me.kolganov.telegramBot.integration.weather.domain;

import lombok.Data;

@Data
public class Weather {
    private String main;
    private String description;
}
