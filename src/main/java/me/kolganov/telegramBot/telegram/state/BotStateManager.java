package me.kolganov.telegramBot.telegram.state;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class BotStateManager {
    private BotState botState = BotState.START;
}
