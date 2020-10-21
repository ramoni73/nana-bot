package me.kolganov.telegramBot.emoji;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emojis {
    THUMBSUP(EmojiParser.parseToUnicode(":thumbsup:")),
    THERMOMETER(EmojiParser.parseToUnicode(":thermometer:")),
    DROPLET(EmojiParser.parseToUnicode(":droplet:")),
    WHITE_CIRCLE(EmojiParser.parseToUnicode(":white_circle:")),
    WALKING(EmojiParser.parseToUnicode(":walking:")),
    VIDEO_GAME(EmojiParser.parseToUnicode(":video_game:")),
    ALARM_CLOCK(EmojiParser.parseToUnicode(":alarm_clock:")),
    FACE_WITH_MONOCLE(EmojiParser.parseToUnicode(":face_with_monocle:")),
    LABEL(EmojiParser.parseToUnicode(":label:"));

    private final String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}
