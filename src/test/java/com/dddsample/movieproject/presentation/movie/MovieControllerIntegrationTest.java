package com.dddsample.movieproject.presentation.movie;

import com.dddsample.movieproject.domain.movie.model.Movie;
import com.dddsample.movieproject.presentation.movie.request.RegisterMovieRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

        String body = objectMapper.writeValueAsString(registerMovieRequest);

        String runningTime = movie.getRunningTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        mockMvc.perform(post("/api/v1/movies")
                .contentType("application/json")
                .content(body))
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

        String body = objectMapper.writeValueAsString(registerMovieRequest);

        mockMvc.perform(post("/api/v1/movies")
                .contentType("application/json")
                .content(body))
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
