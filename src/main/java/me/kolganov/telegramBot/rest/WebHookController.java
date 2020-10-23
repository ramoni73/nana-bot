package me.kolganov.telegramBot.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kolganov.telegramBot.integration.twitch.domain.stream.Stream;
import me.kolganov.telegramBot.telegram.TelegramBot;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebHookController {
    private final TelegramBot telegramBot;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }

    @RequestMapping(value = "/twitch", method = RequestMethod.GET)
    public ResponseEntity<String> twitchWebhook(@RequestParam("hub.challenge") String hubChallenge) {
        log.info("Subscribe token from twitch: " + hubChallenge);
        return ResponseEntity.ok(hubChallenge);
    }

    @RequestMapping(value = "/twitch", method = RequestMethod.POST)
    public ResponseEntity<String> twitchWebhook(@RequestBody Stream stream) {
        telegramBot.sendMessageToTwitchSubs(stream);
        return ResponseEntity.ok().build();
    }
}
