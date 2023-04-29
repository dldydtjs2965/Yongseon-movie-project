package com.dddsample.movieproject.presentation.movie;

import com.dddsample.movieproject.BaseIntegrationTest;
import com.dddsample.movieproject.domain.movie.model.Movie;
import com.dddsample.movieproject.presentation.movie.request.RegisterMovieRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MovieControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void 영화_등록() throws Exception{
        // given
        Movie movie = Movie.builder()
                .title("title")
                .runningTime(LocalTime.of(1, 30))
                .price(10000)
                .build();

        RegisterMovieRequestDto registerMovieRequest = RegisterMovieRequestDto.builder()
                .title(movie.getTitle())
                .runningTime(movie.getRunningTime())
                .price(movie.getPrice())
                .build();

        String payload = toJsonString(registerMovieRequest);

        String runningTime = movie.getRunningTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        mockMvc.perform(post("/api/v1/movies")
                .contentType("application/json")
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.movieId").exists())
                .andExpect(jsonPath("$.movieTitle").value(movie.getTitle()))
                .andExpect(jsonPath("$.movieRunningTime").value(runningTime))
                .andExpect(jsonPath("$.movieAmount").value(movie.getPrice()));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMovie")
    void 영화_등록_값검증_실패(String title, LocalTime runningTime, Integer price) throws Exception{

        RegisterMovieRequestDto registerMovieRequest = RegisterMovieRequestDto.builder()
                .title(title)
                .runningTime(runningTime)
                .price(price)
                .build();

        String payload = toJsonString(registerMovieRequest);

        mockMvc.perform(post("/api/v1/movies")
                .contentType("application/json")
                .content(payload))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> provideInvalidMovie() {
        return Stream.of(
                Arguments.of(" ", LocalTime.of(1, 30), 10000),
                Arguments.of(null, LocalTime.of(1, 30), 10000),
                Arguments.of("title", null, 10000),
                Arguments.of("title", LocalTime.of(1, 30), null),
                Arguments.of("title", LocalTime.of(1, 30), 0),
                Arguments.of("title", LocalTime.of(1, 30), -1),
                Arguments.of("title", LocalTime.of(0, 0), 10000)
        );
    }
}
