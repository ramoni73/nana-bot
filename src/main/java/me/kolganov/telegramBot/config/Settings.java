package me.kolganov.telegramBot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:settings.properties")
public class Settings {
    @Value("${weatherAPI.baseUrl}")
    private String weatherApiBaseUrl;

    @Value("${weatherAPI.key}")
    private String weatherApiKey;

    @Value("${telegram.botUsername}")
    private String telegramBotUsername;

    @Value("${telegram.botToken}")
    private String telegramBotToken;

    @Value("${telegram.botPath}")
    private String telegramBotPath;

    @Value("${twitch.api.channelInfoUrl}")
    private String twitchApiChannelInfoUrl;

    @Value("${twitch.api.gameInfoUrl}")
    private String twitchApiGameInfoUrl;

    @Value("${twitch.api.webhookUrl}")
    private String twitchApiWebhookUrl;

    @Value("${twitch.api.callbackUrl}")
    private String twitchApiCallbackUrl;

    @Value("${twitch.api.streamersUrl}")
    private String twitchApiStreamersUrl;

    @Value("${twitch.api.subscriptionsUrl}")
    private String twitchApiSubscriptionsUrl;

    @Value("${twitch.clientId}")
    private String twitchClientId;

    @Value("${twitch.authorization}")
    private String twitchAuthorization;
}
