package com.dddsample.movieproject.presentation.movie.request;

import com.dddsample.movieproject.common.validator.AfterTime;
import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.movie.model.Movie;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class RegisterMovieRequestDto {
    @NotBlank
    private String title;

    @NotNull
    @AfterTime("00:00")
    private LocalTime runningTime;

    @NotNull
    @Min(1)
    private Integer price;

    public Movie toEntity() {
        return Movie.builder()
                .title(title)
                .runningTime(runningTime)
                .price(Money.of(price))
                .build();
    }
}
