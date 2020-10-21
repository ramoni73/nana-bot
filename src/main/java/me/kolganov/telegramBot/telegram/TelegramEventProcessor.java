package me.kolganov.telegramBot.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kolganov.telegramBot.telegram.state.BotState;
import me.kolganov.telegramBot.telegram.state.BotStateManager;
import me.kolganov.telegramBot.utils.Constants;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramEventProcessor {
    private final TelegramEventHandlerChoicer eventChoicer;
    private final BotStateManager botStateManager;

    public SendMessage processInputMessage(Update update) {
        SendMessage sendMessage = null;

        if (update.hasCallbackQuery()) {
            log.info("Callback query: '{}' from chatId={}, userId={}, username={}",
                    update.getCallbackQuery().getData(),
                    update.getCallbackQuery().getMessage().getChatId(),
                    update.getCallbackQuery().getFrom().getId(),
                    update.getCallbackQuery().getFrom().getUserName());
            return handleCallbackQuery(update.getCallbackQuery());
        }

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("Input message: '{}' from chatId={}, userId={}, username={}",
                    message.getText(),
                    message.getChatId(),
                    message.getFrom().getId(),
                    message.getFrom().getUserName()
            );
//            <3 Пасхалка лучшему в мире другану <3
            if (Constants.VADIK_INPUT.equalsIgnoreCase(message.getText()))
                return new SendMessage(message.getChatId(), Constants.VADIK_OUTPUT);

            sendMessage = handleInputMessage(message);
        }

        log.info("Output message: " + sendMessage);
        return sendMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMessage = message.getText();
        BotState botState;

        switch (inputMessage) {
            case Constants.START:
            case Constants.MENU_INPUT:
                botState = BotState.START;
                break;
            case Constants.WEATHER_INPUT_ASK:
                botState = BotState.WEATHER;
                break;
            case Constants.TWITCH_INPUT_ASK:
                botState = BotState.TWITCH;
                break;
            default:
                botState = botStateManager.getBotState();
                break;
        }

        botStateManager.setBotState(botState);

        return eventChoicer.processInputMessage(botState, message);
    }

    private SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
        return eventChoicer.processCallbackMessage(botStateManager.getBotState(), callbackQuery);
    }
}
