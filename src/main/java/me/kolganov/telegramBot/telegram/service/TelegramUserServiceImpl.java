package me.kolganov.telegramBot.telegram.service;

import lombok.RequiredArgsConstructor;
import me.kolganov.telegramBot.telegram.domain.TelegramUser;
import me.kolganov.telegramBot.telegram.repository.TelegramUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserServiceImpl implements TelegramUserService {
    private final TelegramUserRepository telegramUserRepository;


    @Override
    public TelegramUser getUser(Integer userId, Long chatId) {
        return telegramUserRepository
                .findByUserIdAndChatId(userId, chatId)
                .orElse(TelegramUser.builder()
                        .userId(userId)
                        .chatId(chatId)
                        .twitchStreamersSubscriptions(new ArrayList<>())
                        .build());
    }

    @Override
    public TelegramUser saveUser(TelegramUser telegramUser) {
        return telegramUserRepository.save(telegramUser);
    }

    @Override
    public List<TelegramUser> getAll() {
        return telegramUserRepository.findAll();
    }
}
