package me.kolganov.telegramBot.integration.twitch.domain.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Subscription {
    @JsonProperty("total")
    private int total;

    @JsonProperty("data")
    private List<SubscriptionData> subscriptionData;
}
