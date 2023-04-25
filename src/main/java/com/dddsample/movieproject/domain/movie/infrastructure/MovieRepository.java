package com.dddsample.movieproject.domain.movie.infrastructure;

import com.dddsample.movieproject.domain.movie.model.Movie;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaMovieRepository {

    Movie save(Movie movie);
}
