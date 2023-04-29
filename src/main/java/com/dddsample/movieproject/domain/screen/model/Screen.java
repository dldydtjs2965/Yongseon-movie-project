package com.dddsample.movieproject.domain.screen.model;

import com.dddsample.movieproject.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "screen")
public class Screen extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long movieId;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "tickets"))
    private Tickets tickets;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "remained_tickets"))
    private Tickets remainedTickets;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @Builder
    public Screen(Long movieId, LocalTime runningTime, Tickets tickets, LocalDateTime startedAt) {
        this.movieId = movieId;
        this.tickets = tickets;
        this.startedAt = startedAt;
        this.remainedTickets = Tickets.zeroTickets();
        setEndedAt(runningTime);
    }

    private void setEndedAt(LocalTime runningTime) {
        if (this.startedAt == null) {
            throw new IllegalArgumentException("상영 시작 시간이 null 입니다.");
        }

        if (runningTime == null) {
            throw new IllegalArgumentException("상영 시간이 null 입니다.");
        }

        this.endedAt = this.startedAt.plusSeconds(runningTime.toSecondOfDay());
    }
}
