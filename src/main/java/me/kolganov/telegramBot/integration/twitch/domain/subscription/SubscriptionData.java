package me.kolganov.telegramBot.integration.twitch.domain.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubscriptionData {
    @JsonProperty("topic")
    private String topic;

    @JsonProperty("callback")
    private String callback;

    @JsonProperty("expires_at")
    private String expiresAt;
}
