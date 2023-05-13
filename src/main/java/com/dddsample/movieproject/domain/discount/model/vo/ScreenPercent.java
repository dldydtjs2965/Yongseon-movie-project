package com.dddsample.movieproject.domain.discount.model.vo;

import com.dddsample.movieproject.domain.discount.model.DiscountErrorCode;
import com.dddsample.movieproject.exception.CustomException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class ScreenPercent {
    private final Integer percent;

    private ScreenPercent(Integer value) {
        this.percent = value;
    }

    public ScreenPercent() {
        this.percent = 0;
    }

    public static ScreenPercent of(Integer percent) {
        if (percent < 0 || percent > 100) throw new CustomException(DiscountErrorCode.PERCENT_AMOUNT_INVALID);

        return new ScreenPercent(percent);
    }
}
