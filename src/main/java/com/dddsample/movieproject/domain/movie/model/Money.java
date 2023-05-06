package com.dddsample.movieproject.domain.movie.model;

import com.dddsample.movieproject.domain.discount.model.vo.Amount;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Money {
    private final Integer value;

    private Money(Integer value) {
        this.value = value;
    }

    public Money() {
        this.value = 0;
    }

    public static Money of(Integer value) {
        return new Money(value);
    }

    public boolean isGreaterThan(Amount amount) {
        return this.value > amount.getValue();
    }
}
