package com.dddsample.movieproject.presentation.screen;


import com.dddsample.movieproject.BaseIntegrationTest;
import com.dddsample.movieproject.domain.movie.infrastructure.MovieRepository;
import com.dddsample.movieproject.domain.movie.model.Movie;
import com.dddsample.movieproject.presentation.screen.request.RegisterScreenRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScreenControllerIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private MovieRepository movieRepository;

    private Movie movie;
    @BeforeEach
    void setUp() {
        movie = Movie.builder()
                .title("영화1")
                .runningTime(LocalTime.of(2, 30))
                .price(10000)
                .build();

        movie = movieRepository.save(movie);
    }

    @Test
    void 상영_등록_성공() throws Exception {
        // given
        RegisterScreenRequestDto requestDto = RegisterScreenRequestDto.builder()
                .startedAt(LocalDateTime.of(2023, 4, 1, 12, 0))
                .tickets(100)
                .build();
        // when
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
}
