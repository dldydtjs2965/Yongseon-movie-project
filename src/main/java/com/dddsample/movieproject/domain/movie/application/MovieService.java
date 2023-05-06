package com.dddsample.movieproject.domain.movie.application;

import com.dddsample.movieproject.domain.movie.infrastructure.MovieRepository;
import com.dddsample.movieproject.domain.movie.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    @Transactional
    public Movie register(Movie movie) {
        return movieRepository.save(movie);
    }
}
