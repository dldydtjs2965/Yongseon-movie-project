package com.dddsample.movieproject.domain.screen.model.service;

import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.discount.application.SequenceDiscountService;
import com.dddsample.movieproject.domain.discount.model.vo.SequenceDate;
import com.dddsample.movieproject.domain.movie.infrastructure.MovieRepository;
import com.dddsample.movieproject.domain.movie.model.Movie;
import com.dddsample.movieproject.domain.screen.infrastructure.ScreenRepository;
import com.dddsample.movieproject.domain.screen.model.Screen;
import com.dddsample.movieproject.domain.ticketing.model.Ticketing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReserveScreenService {
    private final ScreenRepository screenRepository;
    private final MovieRepository movieRepository;
    private final SequenceDiscountService sequenceDiscountService;
    public Money reserve(Ticketing ticketing) {
        Screen screen = screenRepository.findById(ticketing.getScreenId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상영관이 없습니다."));

        screen.reserveTicket();

        Movie movie = movieRepository.findById(screen.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("해당 영화가 없습니다."));

        return sequenceDiscountService.calculateDiscount(getScreenSeqDate(screen), movie.getPrice());
    }

    private SequenceDate getScreenSeqDate(Screen screen) {
        List<Screen> todayScreens = screenRepository.findTodayScreens(screen.getStartedAtZero(), screen.getStartedAtTwentyFour());

        return SequenceDate.of(todayScreens.indexOf(screen) + 1, screen.getScreeningDate());
    }
}
