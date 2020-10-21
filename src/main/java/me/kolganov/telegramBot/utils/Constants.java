package me.kolganov.telegramBot.utils;

import me.kolganov.telegramBot.emoji.Emojis;

public class Constants {
    public static final String START = "/start";
    public static final String MENU_INPUT = "В меню";
    public static final String MENU_OUTPUT = "Что будем делать?";
    public static final String WEATHER_INPUT_ASK = "Узнать погоду";
    public static final String WEATHER_OUTPUT_ENTER_CITY = "Введите город";
    public static final String WEATHER_OUTPUT_ERROR = "Город не найден";
    public static final String TWITCH_INPUT_ASK = "Twitch";
    public static final String TWITCH_INPUT_CHECK_SUBSCRIPTIONS = "Мои подписки";
    public static final String TWITCH_OUTPUT_ENTER_STREAMER_NAME = "Введи имя стримера";
    public static final String TWITCH_OUTPUT_STREAMER_NOT_FOUND = "Стример не найден";
    public static final String TWITCH_OUTPUT_GAME_NOT_FOUND = "Игра не найдена";
    public static final String TWITCH_OUTPUT_STREAMER_OFFLINE = " оффлайн";
    public static final String TWITCH_OUTPUT_SUBSCRIBE = "Подписаться на уведомления";
    public static final String TWITCH_OUTPUT_UNSUBSCRIBE = "Отменить подписку";
    public static final String TWITCH_OUTPUT_SUBSCRIBE_SUCCESS = "Подписка оформлена";
    public static final String TWITCH_OUTPUT_UNSUBSCRIBE_SUCCESS = "Подписка отменена";
    public static final String TWITCH_OUTPUT_EMPTY_SUBSCRIPTIONS = "У тебя нет подписок";
    public static final String VADIK_INPUT = "Вадик пидор";
    public static final String VADIK_OUTPUT = "Согласен "  + Emojis.THUMBSUP;
}
