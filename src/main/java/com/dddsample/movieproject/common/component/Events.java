package com.dddsample.movieproject.common.component;

import com.dddsample.movieproject.common.model.BaseEvent;
import org.springframework.context.ApplicationEventPublisher;

public class Events {

    private static ApplicationEventPublisher publisher;

    public void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void raise(BaseEvent event) {
        publisher.publishEvent(event);
    }
}
