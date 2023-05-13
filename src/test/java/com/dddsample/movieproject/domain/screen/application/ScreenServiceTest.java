package com.dddsample.movieproject.domain.screen.application;

import com.dddsample.movieproject.common.utils.CurrentDateTimeUtils;
import com.dddsample.movieproject.domain.screen.infrastructure.ScreenRepository;
import com.dddsample.movieproject.domain.screen.model.Screen;
import com.dddsample.movieproject.domain.screen.model.ScreenErrorCode;
import com.dddsample.movieproject.domain.screen.model.Tickets;
import com.dddsample.movieproject.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScreenServiceTest {

    @InjectMocks
    private ScreenService screenService;

    @Mock
    CurrentDateTimeUtils currentDateTimeUtils;

    @Mock
    ScreenRepository screenRepository;
    @Test
    void 상영_등록() {
        // given
        Screen screen = Screen.builder()
                .runningTime(LocalTime.of(1, 30))
                .movieId(1L)
                .startedAt(LocalDateTime.of(2021, 8, 1, 10, 0))
                .tickets(Tickets.of(100))
                .build();
        // when
        when(currentDateTimeUtils.now()).thenReturn(LocalDateTime.of(2021, 7, 31, 10, 0));
        when(screenRepository.save(screen)).thenReturn(screen);

        screenService.registerScreen(screen);
        // then

        verify(screenRepository).save(screen);
    }

    @Test
    void 과거_상영_등록() {
        Screen screen = Screen.builder()
                .runningTime(LocalTime.of(1, 30))
                .movieId(1L)
                .startedAt(LocalDateTime.of(2021, 7, 31, 10, 0))
                .tickets(Tickets.of(100))
                .build();

        when(currentDateTimeUtils.now()).thenReturn(LocalDateTime.of(2021, 7, 31, 10, 0));

        assertThatThrownBy(() -> screenService.registerScreen(screen))
                .isInstanceOf(CustomException.class)
                .isEqualTo(new CustomException(ScreenErrorCode.INVALID_STARTED_AT));
    }

    @Test
    void 상영_시간_중복_등록() {
        Screen screen = Screen.builder()
                .runningTime(LocalTime.of(1, 30))
                .movieId(1L)
                .startedAt(LocalDateTime.of(2021, 8, 1, 10, 0))
                .tickets(Tickets.of(100))
                .build();

        when(currentDateTimeUtils.now()).thenReturn(LocalDateTime.of(2021, 7, 31, 10, 0));
        when(screenRepository.existsOverlapScreeningTime(screen.getStartedAt(), screen.getEndedAt())).thenReturn(true);

        assertThatThrownBy(() -> screenService.registerScreen(screen))
                .isInstanceOf(CustomException.class)
                .isEqualTo(new CustomException(ScreenErrorCode.SCREENING_TIME_OVERLAP));
    }
}