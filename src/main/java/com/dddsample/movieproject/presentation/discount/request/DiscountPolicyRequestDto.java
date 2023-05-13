package com.dddsample.movieproject.presentation.discount.request;

import com.dddsample.movieproject.domain.discount.model.Discount;
import com.dddsample.movieproject.domain.discount.model.DiscountPolicy;
import com.dddsample.movieproject.domain.discount.model.enumberable.DiscountPolicyType;
import com.dddsample.movieproject.domain.discount.model.vo.ScreenAmount;
import com.dddsample.movieproject.domain.discount.model.vo.ScreenPercent;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DiscountPolicyRequestDto {
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private DiscountPolicyType discountPolicyType;
    @Min(1)
    private Integer discountValue;

    public DiscountPolicy toEntity(Discount discount) {
        switch (discountPolicyType) {
            case AMOUNT:
                return DiscountPolicy.ofAmount(ScreenAmount.of(discountValue), discount);
            case PERCENT:
                return DiscountPolicy.ofPercent(ScreenPercent.of(discountValue), discount);
        }

        throw new IllegalArgumentException("잘못된 할인 정책입니다.");
    }
}
