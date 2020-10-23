package me.kolganov.telegramBot.integration.twitch.domain.channel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelData {
    public ChannelData(String error) {
        this.error = error;
    }

    @JsonProperty("broadcaster_language")
    private String broadcasterLanguage;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("game_id")
    private String gameId;

    @JsonProperty("id")
    private String id;

    @JsonProperty("is_live")
    private boolean isLive;

    @JsonProperty("tags_ids")
    private List<String> tagsIds;

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @JsonProperty("title")
    private String title;

    @JsonProperty("started_at")
    private String startedAt;

    @JsonIgnore
    private String error;
}
