package com.dddsample.movieproject.domain.screen;


import com.dddsample.movieproject.BaseIntegrationTest;
import com.dddsample.movieproject.common.utils.CurrentDateTimeUtils;
import com.dddsample.movieproject.domain.movie.infrastructure.MovieRepository;
import com.dddsample.movieproject.domain.movie.model.Movie;
import com.dddsample.movieproject.domain.movie.model.Money;
import com.dddsample.movieproject.domain.screen.infrastructure.ScreenRepository;
import com.dddsample.movieproject.domain.screen.model.Screen;
import com.dddsample.movieproject.domain.screen.model.ScreenErrorCode;
import com.dddsample.movieproject.domain.screen.model.Tickets;
import com.dddsample.movieproject.presentation.screen.request.RegisterScreenRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScreenIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreenRepository screenRepository;
    @MockBean
    private CurrentDateTimeUtils currentDateTimeUtils;
    private Movie movie;
    private LocalDateTime now = LocalDateTime.of(2023, 4, 1, 12, 0);
    @BeforeEach
    void setUp() {
        movie = Movie.builder()
                .title("영화1")
                .runningTime(LocalTime.of(2, 30))
                .price(Money.of(10000))
                .build();

        movie = movieRepository.save(movie);

        when(currentDateTimeUtils.now())
                .thenReturn(now);
    }

    @Test
    void 상영_등록_성공() throws Exception {
        // given
        RegisterScreenRequestDto requestDto = RegisterScreenRequestDto.builder()
                .startedAt(now.plusDays(1))
                .tickets(100)
                .build();

        String payload = toJsonString(requestDto);
        LocalDateTime endedAt = requestDto.getStartedAt().plusSeconds(movie.getRunningTime().toSecondOfDay());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // then
        mockMvc.perform(post("/api/v1/movies/" + movie.getId() + "/screens")
                        .contentType("application/json")
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.screenId").exists())
                .andExpect(jsonPath("$.movieId").value(movie.getId()))
                .andExpect(jsonPath("$.startedAt").value(requestDto.getStartedAt().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.endedAt").value(endedAt.format(dateTimeFormatter)))
                .andExpect(jsonPath("$.tickets").value(requestDto.getTickets()));

    }

    @ParameterizedTest
    @MethodSource("provideInvalidRegisterScreenRequestDto")
    void 상영등록_값검증_실패(LocalDateTime startedAt, Integer tickets) throws Exception {
        RegisterScreenRequestDto requestDto = RegisterScreenRequestDto.builder()
                .startedAt(startedAt)
                .tickets(tickets)
                .build();

        String payload = toJsonString(requestDto);

        mockMvc.perform(post("/api/v1/movies/" + movie.getId() + "/screens")
                        .contentType("application/json")
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> provideInvalidRegisterScreenRequestDto() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2023, 4, 1, 12, 0), 0),
                Arguments.of(LocalDateTime.of(2023, 4, 1, 12, 0), -1),
                Arguments.of(LocalDateTime.of(2023, 4, 1, 12, 0), null),
                Arguments.of(null, 1000)
        );
    }

    @Test
    void 상영등록_하루전_등록_실패() throws Exception {
        // given

        RegisterScreenRequestDto requestDto = RegisterScreenRequestDto.builder()
                .startedAt(now.minusHours(1))
                .tickets(100)
                .build();

        String payload = toJsonString(requestDto);

        mockMvc.perform(post("/api/v1/movies/" + movie.getId() + "/screens")
                        .contentType("application/json")
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ScreenErrorCode.INVALID_STARTED_AT.getDetail()))
                .andExpect(jsonPath("$.code").value(ScreenErrorCode.INVALID_STARTED_AT.name()));
    }

    @Test
    void 시작시간_겹친_상영등록() throws Exception {
        // given
        saveScreen();

        RegisterScreenRequestDto requestDto = RegisterScreenRequestDto.builder()
                .startedAt(now.plusDays(2).plusHours(1))
                .tickets(100)
                .build();

        String payload = toJsonString(requestDto);

        // then
        mockMvc.perform(post("/api/v1/movies/" + movie.getId() + "/screens")
                        .contentType("application/json")
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ScreenErrorCode.SCREENING_TIME_OVERLAP.getDetail()))
                .andExpect(jsonPath("$.code").value(ScreenErrorCode.SCREENING_TIME_OVERLAP.name()));
    }

    @Test
    void 종료시간_겹친_상영등록() throws Exception {
        // given
        saveScreen();

        RegisterScreenRequestDto requestDto = RegisterScreenRequestDto.builder()
                .startedAt(now.plusDays(2).minusHours(2))
                .tickets(100)
                .build();

        String payload = toJsonString(requestDto);

        // then
        mockMvc.perform(post("/api/v1/movies/" + movie.getId() + "/screens")
                        .contentType("application/json")
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ScreenErrorCode.SCREENING_TIME_OVERLAP.getDetail()))
                .andExpect(jsonPath("$.code").value(ScreenErrorCode.SCREENING_TIME_OVERLAP.name()));
    }

    void saveScreen() {
        Screen newScreen = Screen.builder()
                .movieId(movie.getId())
                .runningTime(movie.getRunningTime())
                .startedAt(now.plusDays(2))
                .tickets(Tickets.of(100))
                .build();

        screenRepository.save(newScreen);
    }
}
