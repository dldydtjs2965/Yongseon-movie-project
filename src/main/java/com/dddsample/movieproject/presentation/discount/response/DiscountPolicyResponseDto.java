package com.dddsample.movieproject.presentation.discount.response;

import com.dddsample.movieproject.domain.discount.model.DiscountPolicy;
import com.dddsample.movieproject.domain.discount.model.enumberable.DiscountPolicyType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class DiscountPolicyResponseDto {
    private Integer discountValue;
    @Enumerated(value = EnumType.STRING)
    private DiscountPolicyType discountPolicyType;

    public DiscountPolicyResponseDto(DiscountPolicy discountPolicy) {
        this.discountValue = discountPolicy.getDiscountValue();
        this.discountPolicyType = discountPolicy.getPolicyType();
    }
}
