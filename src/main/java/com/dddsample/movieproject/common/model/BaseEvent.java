package com.dddsample.movieproject.common.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseEvent {
    private final LocalDateTime eventTimestamp;

    public BaseEvent() {
        this.eventTimestamp = LocalDateTime.now();
    }
}
