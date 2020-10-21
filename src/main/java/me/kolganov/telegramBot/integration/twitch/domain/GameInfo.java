package me.kolganov.telegramBot.integration.twitch.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GameInfo {
    @JsonProperty("data")
    private List<GameData> gameDataList;

    @JsonIgnore
    private String error;
}
