package com.dddsample.movieproject.presentation.movie.response;

import com.dddsample.movieproject.domain.movie.model.Movie;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class RegisterMovieResponseDto {

    private Long movieId;
    private String movieTitle;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime movieRunningTime;
    private Integer movieAmount;

    public RegisterMovieResponseDto(Movie movie) {
        this.movieId = movie.getId();
        this.movieTitle = movie.getTitle();
        this.movieRunningTime = movie.getRunningTime();
        this.movieAmount = movie.getPrice();
    }
}
