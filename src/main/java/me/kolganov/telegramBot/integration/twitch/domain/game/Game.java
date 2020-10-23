package me.kolganov.telegramBot.integration.twitch.domain.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Game {
    @JsonProperty("data")
    private List<GameData> gameDataList;

    @JsonIgnore
    private String error;
}
