package com.dddsample.movieproject.domain.screen.infrastructure;

import com.dddsample.movieproject.domain.screen.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaScreenRepository extends JpaRepository<Screen, Long> {
}
