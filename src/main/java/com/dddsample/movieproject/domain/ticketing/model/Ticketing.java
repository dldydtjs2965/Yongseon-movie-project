package com.dddsample.movieproject.domain.ticketing.model;

import com.dddsample.movieproject.common.model.BaseTimeEntity;
import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.ticketing.model.enumerable.TicketingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Ticketing extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long screenId;

    private Long userId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private Money price;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "paid_money"))
    private Money paidMoney;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "refund_money"))
    private Money refundMoney;

    @Enumerated(value = EnumType.STRING)
    private TicketingStatus ticketingStatus;

    private LocalDateTime paidAt;

    private LocalDateTime canceledAt;
}
