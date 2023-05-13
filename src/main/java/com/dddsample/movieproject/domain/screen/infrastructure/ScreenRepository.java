package com.dddsample.movieproject.domain.screen.infrastructure;

import com.dddsample.movieproject.domain.screen.model.Screen;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreenRepository extends Repository<Screen, Long> {

    Optional<Screen> findById(Long id);

    Screen save(Screen screen);

    @Query("select count(s) > 0 from Screen s where s.endedAt >= :startedAt and  s.startedAt <= :endedAt")
    boolean existsOverlapScreeningTime(LocalDateTime startedAt, LocalDateTime endedAt);


    @Query("select s from Screen s where s.endedAt >= :startedAt and  s.startedAt <= :endedAt")
    List<Screen> findTodayScreens(LocalDateTime startedAt, LocalDateTime endedAt);
}
