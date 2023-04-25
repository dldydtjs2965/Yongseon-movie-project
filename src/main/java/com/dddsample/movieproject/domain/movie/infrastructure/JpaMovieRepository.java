package com.dddsample.movieproject.domain.movie.infrastructure;

import com.dddsample.movieproject.domain.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaMovieRepository extends JpaRepository<Movie, Long> {
}
