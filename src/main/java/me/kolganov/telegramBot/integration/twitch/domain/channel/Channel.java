package me.kolganov.telegramBot.integration.twitch.domain.channel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Channel {
    @JsonProperty("data")
    private List<ChannelData> channelDataList;

    @JsonIgnore
    private String error;
}
