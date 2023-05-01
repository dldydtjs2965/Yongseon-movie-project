package com.dddsample.movieproject.domain.screen.application;

import com.dddsample.movieproject.common.utils.CurrentDateTimeUtils;
import com.dddsample.movieproject.domain.screen.infrastructure.ScreenRepository;
import com.dddsample.movieproject.domain.screen.model.Screen;
import com.dddsample.movieproject.domain.screen.model.ScreenErrorCode;
import com.dddsample.movieproject.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final CurrentDateTimeUtils currentDateTimeUtils;
    @Transactional
    public Screen registerScreen(Screen screen) {
        screen.checkStartedAt(currentDateTimeUtils.now());

        if (checkScreeningTimeOverlap(screen)) {
            throw new CustomException(ScreenErrorCode.SCREENING_TIME_OVERLAP);
        }

        return screenRepository.save(screen);
    }

    private boolean checkScreeningTimeOverlap(Screen screen) {
        return screenRepository.existsOverlapScreeningTime(screen.getStartedAt(), screen.getEndedAt());
    }
}
