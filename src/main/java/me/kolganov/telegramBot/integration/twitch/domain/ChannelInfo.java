package me.kolganov.telegramBot.integration.twitch.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChannelInfo {
    @JsonProperty("data")
    private List<ChannelData> channelDataList;

    @JsonIgnore
    private String error;
}
