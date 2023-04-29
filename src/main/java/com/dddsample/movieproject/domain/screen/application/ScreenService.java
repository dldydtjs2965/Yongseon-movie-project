package com.dddsample.movieproject.domain.screen.application;

import com.dddsample.movieproject.common.utils.CurrentDateTimeUtils;
import com.dddsample.movieproject.domain.screen.infrastructure.ScreenRepository;
import com.dddsample.movieproject.domain.screen.model.Screen;
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

        return screenRepository.save(screen);
    }
}
