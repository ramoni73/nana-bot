package me.kolganov.telegramBot.integration.twitch.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kolganov.telegramBot.config.Settings;
import me.kolganov.telegramBot.integration.twitch.domain.Hub;
import me.kolganov.telegramBot.integration.twitch.domain.channel.ChannelData;
import me.kolganov.telegramBot.integration.twitch.domain.channel.Channel;
import me.kolganov.telegramBot.integration.twitch.domain.game.Game;
import me.kolganov.telegramBot.integration.twitch.domain.subscription.Subscription;
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
    public Channel getChanel(String channelName) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("client-id", settings.getTwitchClientId());
        httpHeaders.set("Authorization", settings.getTwitchAuthorization());

        HttpEntity<Channel> entity = new HttpEntity<>(httpHeaders);

        String url = settings.getTwitchApiChannelInfoUrl();

        Map<String, String> param = new HashMap<>();
        param.put("query", channelName);

        ResponseEntity<Channel> response = restTemplate
                .exchange(url, HttpMethod.GET, entity, Channel.class, param);

        Channel channel;
        try {
            channel = response.getBody();
        } catch (Exception e) {
            channel = new Channel();
            channel.setError(Constants.TWITCH_OUTPUT_STREAMER_NOT_FOUND);
        }

        log.info("ChannelInfo response: response_code={}, body={}", response.getStatusCode(), response.getBody());
        return channel;
    }

    @Override
    public Game getGame(String gameId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("client-id", settings.getTwitchClientId());
        httpHeaders.set("Authorization", settings.getTwitchAuthorization());

        HttpEntity<Channel> entity = new HttpEntity<>(httpHeaders);

        String url = settings.getTwitchApiGameInfoUrl();

        Map<String, String> param = new HashMap<>();
        param.put("id", gameId);

        ResponseEntity<Game> response = restTemplate
                .exchange(url, HttpMethod.GET, entity, Game.class, param);

        Game game;
        try {
            game = response.getBody();
        } catch (Exception e) {
            game = new Game();
            game.setError(Constants.TWITCH_OUTPUT_GAME_NOT_FOUND);
        }

        log.info("GameInfo response: response_code={}, body={}", response.getStatusCode(), response.getBody());
        return game;
    }

    @Override
    public void subscribe(String streamerId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("client-id", settings.getTwitchClientId());
        httpHeaders.set("Authorization", settings.getTwitchAuthorization());

        String url = settings.getTwitchApiWebhookUrl();

        Hub hub = Hub.builder()
                .hubCallback(settings.getTwitchApiCallbackUrl())
                .hubMode("subscribe")
                .hubTopic(settings.getTwitchApiStreamersUrl() + streamerId)
                .hubLeaseSeconds(864000)
                .build();

        HttpEntity<Hub> entity = new HttpEntity<>(hub, httpHeaders);

        ResponseEntity<String> response = restTemplate
                .postForEntity(url, entity, String.class);

        log.info("Twitch sub response: response_code={}, body={}", response.getStatusCode(), response.getBody());
    }

    @Override
    public Subscription checkSubscriptions() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("client-id", settings.getTwitchClientId());
        httpHeaders.set("Authorization", settings.getTwitchAuthorization());

        HttpEntity<Channel> entity = new HttpEntity<>(httpHeaders);

        String url = settings.getTwitchApiSubscriptionsUrl();

        ResponseEntity<Subscription> response = restTemplate
                .exchange(url, HttpMethod.GET, entity, Subscription.class);

        log.info("Twitch subscriptions response: response_code={}, body={}", response.getStatusCode(), response.getBody());
        return response.getBody();
    }
}
