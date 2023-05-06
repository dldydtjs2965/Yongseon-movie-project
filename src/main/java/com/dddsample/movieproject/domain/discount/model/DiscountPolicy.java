package com.dddsample.movieproject.domain.discount.model;

import com.dddsample.movieproject.common.model.BaseTimeEntity;
import com.dddsample.movieproject.domain.discount.model.enumberable.DiscountPolicyType;
import com.dddsample.movieproject.domain.discount.model.vo.Amount;
import com.dddsample.movieproject.domain.discount.model.vo.PercentAmount;
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
    private Amount amount;

    @Embedded
    @AttributeOverride(name = "percent", column = @Column(name = "percent"))
    private PercentAmount percent;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "policy_type", nullable = false)
    private DiscountPolicyType policyType;

    public static DiscountPolicy ofAmount(Amount amount, Discount discount) {
        return DiscountPolicy.builder()
                .amount(amount)
                .discount(discount)
                .policyType(DiscountPolicyType.AMOUNT)
                .build();
    }

    public static DiscountPolicy ofPercent(PercentAmount percent, Discount discount) {
        return DiscountPolicy.builder()
                .percent(percent)
                .discount(discount)
                .policyType(DiscountPolicyType.PERCENT)
                .build();
    }

    public Integer getDiscountValue() {
        switch (this.policyType) {
            case AMOUNT:
                return amount.getValue();
            case PERCENT:
                return percent.getPercent();
        }

        throw new IllegalArgumentException("잘못된 할인 정책입니다.");
    }
}
