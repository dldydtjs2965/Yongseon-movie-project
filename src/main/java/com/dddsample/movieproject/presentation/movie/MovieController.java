package com.dddsample.movieproject.presentation.movie;

import com.dddsample.movieproject.domain.movie.application.MovieService;
import com.dddsample.movieproject.domain.movie.model.Movie;
import com.dddsample.movieproject.presentation.movie.request.RegisterMovieRequestDto;
import com.dddsample.movieproject.presentation.movie.response.RegisterMovieResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    @PostMapping("")
    public ResponseEntity<RegisterMovieResponseDto> registerMovie (
            @RequestBody RegisterMovieRequestDto registerMovieRequestDto
    ) {

        Movie registeredMovie = movieService.register(registerMovieRequestDto.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterMovieResponseDto(registeredMovie));
    }
}
