package com.dddsample.movieproject.domain.discount.model.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Amount {

    private final Integer value;

    private Amount(Integer value) {
        this.value = value;
    }

    public Amount() {
        this.value = 0;
    }

    public static Amount of(Integer value) {
        return new Amount(value);
    }
}
