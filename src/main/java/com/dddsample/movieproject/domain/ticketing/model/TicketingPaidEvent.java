package com.dddsample.movieproject.domain.ticketing.model;

import com.dddsample.movieproject.common.model.BaseEvent;
import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.payment.model.vo.Payment;

public class TicketingPaidEvent extends BaseEvent {
    private Money paidMoney;
    private Payment payment;

    public TicketingPaidEvent(Payment payment, Money paidMoney) {
        this.payment = payment;
        this.paidMoney = paidMoney;
    }
}
