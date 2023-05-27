package com.dddsample.movieproject.domain.ticketing.model;

import com.dddsample.movieproject.common.component.Events;
import com.dddsample.movieproject.common.model.BaseTimeEntity;
import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.payment.model.vo.Payment;
import com.dddsample.movieproject.domain.screen.model.Tickets;
import com.dddsample.movieproject.domain.ticketing.model.enumerable.TicketingStatus;
import com.dddsample.movieproject.exception.CustomException;
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

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "reserved_tickets"))
    private Tickets reservedTickets;

    private LocalDateTime paidAt;

    private LocalDateTime canceledAt;

    // 티켓 결제 메서드
    public void pay(Payment payment, Money payableMoney) {
        if (this.ticketingStatus != TicketingStatus.RESERVED) {
            throw new CustomException(TicketingErrorCode.TICKETING_NOT_RESERVED);
        }

        this.paidMoney = payableMoney;
        this.ticketingStatus = TicketingStatus.PAID;
        this.paidAt = LocalDateTime.now();
        Events.raise(new TicketingPaidEvent(payment, payableMoney));
    }

    public void cancel() {
        if (this.ticketingStatus != TicketingStatus.PAID) {
            throw new CustomException(TicketingErrorCode.PAID_TICKET_ONLY_CANCEL);
        }

        this.ticketingStatus = TicketingStatus.CANCELED;
        this.canceledAt = LocalDateTime.now();
        Events.raise(new TicketingCanceledEvent(this.paidMoney));
    }
}
