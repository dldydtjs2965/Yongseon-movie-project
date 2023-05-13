package com.dddsample.movieproject.domain.discount.model;

import com.dddsample.movieproject.common.model.BaseTimeEntity;
import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.discount.model.enumberable.DiscountPolicyType;
import com.dddsample.movieproject.domain.discount.model.vo.ScreenAmount;
import com.dddsample.movieproject.domain.discount.model.vo.ScreenPercent;
import com.dddsample.movieproject.exception.CustomException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountPolicy extends BaseTimeEntity {
    @Id
    @Column(name = "discount_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "amount"))
    private ScreenAmount screenAmount;

    @Embedded
    @AttributeOverride(name = "percent", column = @Column(name = "percent"))
    private ScreenPercent percent;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "policy_type", nullable = false)
    private DiscountPolicyType policyType;

    public static DiscountPolicy ofAmount(ScreenAmount screenAmount, Discount discount) {
        return DiscountPolicy.builder()
                .screenAmount(screenAmount)
                .discount(discount)
                .policyType(DiscountPolicyType.AMOUNT)
                .build();
    }

    public static DiscountPolicy ofPercent(ScreenPercent percent, Discount discount) {
        return DiscountPolicy.builder()
                .percent(percent)
                .discount(discount)
                .policyType(DiscountPolicyType.PERCENT)
                .build();
    }

    public Integer getDiscountValue() {
        switch (this.policyType) {
            case AMOUNT:
                return screenAmount.getValue();
            case PERCENT:
                return percent.getPercent();
        }

        throw new IllegalArgumentException("잘못된 할인 정책입니다.");
    }

    public Money calculateDiscount(Money amount) {
        switch (this.policyType) {
            case AMOUNT:
                if (amount.isLessThan(amount))
                    throw new CustomException(DiscountErrorCode.OVER_DISCOUNT_AMOUNT);
                return amount.minus(screenAmount.toMoney());
            case PERCENT:
                return amount.minus(amount.timesPercent(percent.getPercent()));
        }

        throw new IllegalArgumentException("잘못된 할인 정책입니다.");
    }
}
