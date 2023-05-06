package com.dddsample.movieproject.domain.discount.model.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class PercentAmount {
    private final Integer percent;

    private PercentAmount(Integer value) {
        this.percent = value;
    }

    public PercentAmount() {
        this.percent = 0;
    }

    public static PercentAmount of(Integer percent) {
        return new PercentAmount(percent);
    }
}
