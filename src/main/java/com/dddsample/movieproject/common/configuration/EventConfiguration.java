package com.dddsample.movieproject.common.configuration;

import com.dddsample.movieproject.common.component.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventConfiguration {
    private final ApplicationContext applicationContext;

    @Bean
    public Events events() {
        Events events = new Events();
        events.setPublisher(applicationContext);
        return events;
    }
}
