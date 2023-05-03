package com.dddsample.movieproject.screen.request;

import com.dddsample.movieproject.domain.movie.model.Movie;
import com.dddsample.movieproject.domain.screen.model.Screen;
import com.dddsample.movieproject.domain.screen.model.Tickets;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class RegisterScreenRequestDto {
    @NotNull
    private LocalDateTime startedAt;
    @Min(1)
    @NotNull
    private Integer tickets;

    public Screen toEntity(Movie movie) {
        return Screen.builder()
                .startedAt(startedAt)
                .tickets(Tickets.of(tickets))
                .runningTime(movie.getRunningTime())
                .movieId(movie.getId())
                .build();
    }
}
