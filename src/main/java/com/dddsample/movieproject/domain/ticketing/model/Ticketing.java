package com.dddsample.movieproject.domain.ticketing.model;

import com.dddsample.movieproject.common.model.BaseTimeEntity;
import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.ticketing.model.enumerable.TicketingStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
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

    @Enumerated(value = EnumType.STRING)
    private TicketingStatus ticketingStatus;

    private LocalDateTime paidAt;

    private LocalDateTime canceledAt;
}
