package com.dddsample.movieproject.domain.screen.model;

import com.dddsample.movieproject.common.model.BaseTimeEntity;
import com.dddsample.movieproject.exception.CustomException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;


@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "screen")
public class Screen extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long movieId;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "tickets", nullable = false))
    private Tickets tickets;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "remained_tickets", nullable = false))
    private Tickets remainedTickets;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at", nullable = false)
    private LocalDateTime endedAt;

    @Builder
    public Screen(Long movieId, LocalTime runningTime, Tickets tickets, LocalDateTime startedAt) {
        this.movieId = movieId;
        this.startedAt = startedAt;
        this.tickets = tickets;
        this.remainedTickets = tickets;
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

    public void checkStartedAt(LocalDateTime now) {
        if (startTimeTwentyFourHoursAfter(now)) {
            return;
        }

        throw new CustomException(ScreenErrorCode.INVALID_STARTED_AT);
    }

    private boolean startTimeTwentyFourHoursAfter (LocalDateTime now) {
        if (this.startedAt == null) {
            throw new IllegalArgumentException("상영 시작 시간이 null 입니다.");
        }

        LocalDateTime twentyFourHoursAfter = now.plusDays(1);

        return this.startedAt.isAfter(twentyFourHoursAfter) || this.startedAt.isEqual(twentyFourHoursAfter);
    }

    public void reserveTicket() {
        if (this.remainedTickets.isEnough(1)) {
            this.remainedTickets.minus(1);
            return;
        }

        throw new CustomException(ScreenErrorCode.NOT_ENOUGH_TICKETS);
    }

    public LocalDateTime getStartedAtZero() {
        return this.startedAt.toLocalDate().atStartOfDay();
    }

    public LocalDateTime getStartedAtTwentyFour() {
        return this.startedAt.toLocalDate().atTime(LocalTime.MAX);
    }

    public LocalDate getScreeningDate() {
        return this.startedAt.toLocalDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screen screen = (Screen) o;
        return Objects.equals(id, screen.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
