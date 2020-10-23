package me.kolganov.telegramBot.integration.twitch.service;

import me.kolganov.telegramBot.integration.twitch.domain.channel.ChannelData;
import me.kolganov.telegramBot.integration.twitch.domain.channel.Channel;
import me.kolganov.telegramBot.integration.twitch.domain.game.Game;
import me.kolganov.telegramBot.integration.twitch.domain.subscription.Subscription;

public interface TwitchService {
    /**
     * Get information about channel by channel name (streamer name)
     * @param channelName - name of channel (streamer
     * @return - Channel
     */
    Channel getChanel(String channelName);

    /**
     * Get information about game by game id
     * @param gameId - id of game
     * @return - Game
     */
    Game getGame(String gameId);

    /**
     * Send request to Twitch to subscribe on Topic: Stream Changed
     * @param streamerId - id of streamer
     */
    void subscribe(String streamerId);

    /**
     * Check all subscriptions of all Topics
     * @return - Subscription
     */
    Subscription checkSubscriptions();
}
