package com.dddsample.movieproject.common.model;

import com.dddsample.movieproject.domain.discount.model.vo.ScreenAmount;
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

    public Money multiply(Integer count) {
        return new Money(this.value * count);
    }

    public Boolean isLessThan(Money other) {
        return this.value < other.value;
    }

    public Money minus(Money other) {
        if (this.isLessThan(other)) {
            throw new IllegalArgumentException("금액이 부족합니다.");
        }

        return new Money(this.value - other.value);
    }

    public Money timesPercent(Integer percent) {
        if (percent < 0) {
            throw new IllegalArgumentException("할인율은 0보다 작을 수 없습니다.");
        }

        if (percent > 100) {
            throw new IllegalArgumentException("할인율은 100보다 클 수 없습니다.");
        }

        return new Money(this.value * percent / 100);
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return this.value >= other.value;
    }
}
