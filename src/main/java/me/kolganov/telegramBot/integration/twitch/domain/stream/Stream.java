package me.kolganov.telegramBot.integration.twitch.domain.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Stream {
    @JsonProperty("data")
    private List<StreamData> streamData;
}
