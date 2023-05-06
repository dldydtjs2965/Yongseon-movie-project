package com.dddsample.movieproject.domain.discount.model;

import com.dddsample.movieproject.common.model.BaseTimeEntity;
import com.dddsample.movieproject.domain.discount.model.enumberable.DiscountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discount_type")
public class Discount extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private DiscountStatus discountStatus;

    private LocalDateTime appliedAt;

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL, optional = false, orphanRemoval = true, mappedBy = "discount")
    private DiscountPolicy discountPolicy;

    public void updateDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
