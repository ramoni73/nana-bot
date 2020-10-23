package me.kolganov.telegramBot.integration.twitch.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hub {
    @JsonProperty("hub.callback")
    private String hubCallback;

    @JsonProperty("hub.mode")
    private String hubMode;

    @JsonProperty("hub.topic")
    private String hubTopic;

    @JsonProperty("hub.lease_seconds")
    private int hubLeaseSeconds;
}
