package com.dddsample.movieproject.domain.payment.infrastructure;

import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.payment.model.vo.Payment;

public interface PaymentProcessor {

    void pay(Payment payment, Money payableMoney);
}
