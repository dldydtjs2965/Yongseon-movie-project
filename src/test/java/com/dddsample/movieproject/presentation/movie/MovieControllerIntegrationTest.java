package com.dddsample.movieproject.presentation.movie;

import com.dddsample.movieproject.domain.movie.model.Movie;
import com.dddsample.movieproject.presentation.movie.request.RegisterMovieRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerIntegrationTest {

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
                .amount(10000)
                .build();

        RegisterMovieRequestDto registerMovieRequest = RegisterMovieRequestDto.builder()
                .title(movie.getTitle())
                .runningTime(movie.getRunningTime())
                .amount(movie.getAmount())
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
                .andExpect(jsonPath("$.movieAmount").value(movie.getAmount()));
    }
}
