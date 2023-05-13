package com.dddsample.movieproject.domain.discount.model.vo;

import com.dddsample.movieproject.common.model.Money;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class ScreenAmount {

    private final Integer value;

    private ScreenAmount(Integer value) {
        this.value = value;
    }

    public ScreenAmount() {
        this.value = 0;
    }

    public static ScreenAmount of(Integer value) {
        return new ScreenAmount(value);
    }

    public Money toMoney() {
        return Money.of(this.value);
    }
}
