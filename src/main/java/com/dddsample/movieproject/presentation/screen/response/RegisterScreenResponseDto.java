package com.dddsample.movieproject.presentation.screen.response;

import com.dddsample.movieproject.domain.screen.model.Screen;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RegisterScreenResponseDto {
    private Long screenId;
    private Long movieId;
    private Integer tickets;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endedAt;

    public RegisterScreenResponseDto(Screen screen) {
        this.screenId = screen.getId();
        this.movieId = screen.getMovieId();
        this.tickets = screen.getTickets().getValue();
        this.startedAt = screen.getStartedAt();
        this.endedAt = screen.getEndedAt();
    }
}
