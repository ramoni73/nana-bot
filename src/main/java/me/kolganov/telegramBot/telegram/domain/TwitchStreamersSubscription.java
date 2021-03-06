package me.kolganov.telegramBot.telegram.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "twitch_streamers_subscription")
@NoArgsConstructor
@AllArgsConstructor
public class TwitchStreamersSubscription implements Comparable<TwitchStreamersSubscription> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "streamer")
    private String streamer;

    @ToString.Exclude
    @ManyToOne(targetEntity = TelegramUser.class)
    @JoinColumn(name = "telegram_user_id")
    private TelegramUser telegramUser;

    @Override
    public int compareTo(TwitchStreamersSubscription o) {
        return this.streamer.compareTo(o.getStreamer());
    }
}
