package com.dddsample.movieproject.domain.movie.infrastructure;

import com.dddsample.movieproject.domain.movie.model.Movie;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MovieRepository extends Repository<Movie, Long> {
        Optional<Movie> findById(Long id);

        Movie save(Movie movie);
}
