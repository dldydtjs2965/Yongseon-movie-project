package com.dddsample.movieproject.common.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CurrentDateTimeUtils {
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
