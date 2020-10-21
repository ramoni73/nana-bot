package me.kolganov.telegramBot.telegram.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "telegram_users")
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "username")
    private String username;

    @ToString.Exclude
    @OneToMany(mappedBy = "telegramUser")
    private List<TwitchStreamersSubscription> twitchStreamersSubscriptions;
}
