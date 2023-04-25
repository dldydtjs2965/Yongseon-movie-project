package com.dddsample.movieproject.presentation.movie.request;

import com.dddsample.movieproject.domain.movie.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class RegisterMovieRequestDto {
    private String title;
    private LocalTime runningTime;
    private Integer price;

    public Movie toEntity() {
        return Movie.builder()
                .title(title)
                .runningTime(runningTime)
                .price(price)
                .build();
    }
}
