package com.dddsample.movieproject.domain.screen.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Tickets {
    private Integer value;

    public Tickets(Integer value) {
        this.value = value;
    }

    public static Tickets of(Integer value) {
        return new Tickets(value);
    }

    public void minus(Integer tickets) {
        this.value -= tickets;
    }

    public boolean isEnough(Integer tickets) {
        return this.value >= tickets;
    }
}
