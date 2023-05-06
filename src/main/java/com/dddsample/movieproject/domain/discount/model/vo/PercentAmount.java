package com.dddsample.movieproject.domain.discount.model.vo;

import com.dddsample.movieproject.domain.discount.model.DiscountErrorCode;
import com.dddsample.movieproject.exception.CustomException;
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
        if (percent < 0 || percent > 100) throw new CustomException(DiscountErrorCode.PERCENT_AMOUNT_INVALID);

        return new PercentAmount(percent);
    }
}
