package me.kolganov.telegramBot.integration.twitch.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kolganov.telegramBot.config.Settings;
import me.kolganov.telegramBot.integration.twitch.domain.ChannelInfo;
import me.kolganov.telegramBot.integration.twitch.domain.GameInfo;
import me.kolganov.telegramBot.utils.Constants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwitchServiceImpl implements TwitchService {
    private final Settings settings;

    @Override
    public ChannelInfo getChanelInfo(String channelName) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("client-id", settings.getTwitchClientId());
        httpHeaders.set("Authorization", settings.getTwitchAuthorization());

        HttpEntity<ChannelInfo> entity = new HttpEntity<>(httpHeaders);

        String url = settings.getTwitchApiChannelInfoUrl();

        Map<String, String> param = new HashMap<>();
        param.put("query", channelName);

        ResponseEntity<ChannelInfo> response = restTemplate
                .exchange(url, HttpMethod.GET, entity, ChannelInfo.class, param);

        ChannelInfo channelInfo;
        try {
            channelInfo = response.getBody();
        } catch (Exception e) {
            channelInfo = new ChannelInfo();
            channelInfo.setError(Constants.TWITCH_OUTPUT_STREAMER_NOT_FOUND);
        }

        log.info("channelInfo: " + channelInfo);
        return channelInfo;
    }

    @Override
    public GameInfo getGameInfo(String gameId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("client-id", settings.getTwitchClientId());
        httpHeaders.set("Authorization", settings.getTwitchAuthorization());

        HttpEntity<ChannelInfo> entity = new HttpEntity<>(httpHeaders);

        String url = settings.getTwitchApiGameInfoUrl();

        Map<String, String> param = new HashMap<>();
        param.put("id", gameId);

        ResponseEntity<GameInfo> response = restTemplate
                .exchange(url, HttpMethod.GET, entity, GameInfo.class, param);

        GameInfo gameInfo;
        try {
            gameInfo = response.getBody();
        } catch (Exception e) {
            gameInfo = new GameInfo();
            gameInfo.setError(Constants.TWITCH_OUTPUT_GAME_NOT_FOUND);
        }

        log.info("gameInfo: " + gameInfo);
        return gameInfo;
    }
}
