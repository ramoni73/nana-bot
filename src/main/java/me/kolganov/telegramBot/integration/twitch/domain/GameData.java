package me.kolganov.telegramBot.integration.twitch.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GameData {
    @JsonProperty("box_art_url")
    private String boxArtUrl;

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
}
