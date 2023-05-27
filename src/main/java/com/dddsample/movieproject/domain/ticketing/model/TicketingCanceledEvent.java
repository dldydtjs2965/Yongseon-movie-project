package com.dddsample.movieproject.domain.ticketing.model;

import com.dddsample.movieproject.common.model.BaseEvent;
import com.dddsample.movieproject.common.model.Money;

public class TicketingCanceledEvent extends BaseEvent {
    private Money refundMoney;

    public TicketingCanceledEvent(Money refundMoney) {
        this.refundMoney = refundMoney;
    }
}
