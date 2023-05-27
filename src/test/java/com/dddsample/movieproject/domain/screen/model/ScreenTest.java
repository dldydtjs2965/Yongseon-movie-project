package com.dddsample.movieproject.domain.screen.model;

import com.dddsample.movieproject.exception.CustomException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ScreenTest {

    @Test
    void 상영_날짜_검증() {
        Screen screen = createScreen();

        assertThatThrownBy(() -> screen.checkStartedAt(LocalDateTime.of(2021, 8, 2, 10, 0)))
                .isInstanceOf(CustomException.class)
                .isEqualTo(new CustomException(ScreenErrorCode.INVALID_STARTED_AT));
    }

    private Screen createScreen() {
        return Screen.builder()
                .runningTime(LocalTime.of(1, 30))
                .movieId(1L)
                .startedAt(LocalDateTime.of(2021, 8, 1, 10, 0))
                .tickets(Tickets.of(100))
                .build();
    }
}