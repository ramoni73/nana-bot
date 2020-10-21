package me.kolganov.telegramBot.integration.twitch.service;

import me.kolganov.telegramBot.integration.twitch.domain.ChannelInfo;
import me.kolganov.telegramBot.integration.twitch.domain.GameInfo;

public interface TwitchService {
    ChannelInfo getChanelInfo(String channelName);
    GameInfo getGameInfo(String gameId);
}
