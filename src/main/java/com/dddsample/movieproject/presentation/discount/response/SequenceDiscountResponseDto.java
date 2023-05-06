package com.dddsample.movieproject.presentation.discount.response;

import com.dddsample.movieproject.domain.discount.model.SequenceDiscount;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SequenceDiscountResponseDto {
    private Long discountId;

    private String sequence;

    private LocalDate discountBaseDate;

    private DiscountPolicyResponseDto discountPolicy;

    public SequenceDiscountResponseDto(SequenceDiscount sequenceDiscount) {
        this.discountId = sequenceDiscount.getId();
        this.sequence = sequenceDiscount.getSequence();
        this.discountBaseDate = sequenceDiscount.getDiscountBaseDate();
        this.discountPolicy = new DiscountPolicyResponseDto(sequenceDiscount.getDiscountPolicy());
    }
}
