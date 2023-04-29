package com.dddsample.movieproject.presentation.screen;

import com.dddsample.movieproject.domain.movie.model.Movie;
import com.dddsample.movieproject.domain.screen.application.ScreenService;
import com.dddsample.movieproject.domain.screen.model.Screen;
import com.dddsample.movieproject.presentation.screen.request.RegisterScreenRequestDto;
import com.dddsample.movieproject.presentation.screen.response.RegisterScreenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies/{movieId}/screens")
public class ScreenController {
    private final ScreenService screenService;
    @PostMapping("")
    public ResponseEntity registerScreen(
            @PathVariable("movieId")Movie movie,
            @RequestBody RegisterScreenRequestDto registerScreenRequest
    ) {

        Screen newScreen = registerScreenRequest.toEntity(movie);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterScreenResponseDto(screenService.registerScreen(newScreen)));
    }
}
