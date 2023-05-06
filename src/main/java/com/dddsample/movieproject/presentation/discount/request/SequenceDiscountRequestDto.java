package com.dddsample.movieproject.presentation.discount.request;

import com.dddsample.movieproject.domain.discount.model.SequenceDiscount;
import com.dddsample.movieproject.domain.discount.model.enumberable.DiscountStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SequenceDiscountRequestDto {

    @Min(1)
    @NotNull
    private Integer discountSequence;

    @NotNull
    private LocalDate discountBaseDate;

    @NotNull
    private DiscountPolicyRequestDto discountPolicy;

    public SequenceDiscount toEntity() {
        SequenceDiscount sequenceDiscount =  SequenceDiscount.builder()
                .sequence(discountSequence.toString())
                .discountBaseDate(discountBaseDate)
                .discountStatus(DiscountStatus.WAITING)
                .build();

        sequenceDiscount.updateDiscountPolicy(discountPolicy.toEntity(sequenceDiscount));

        return sequenceDiscount;
    }
}
