package com.dddsample.movieproject.domain.payment.infrastructure;

import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.payment.model.vo.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessorImpl implements PaymentProcessor{
    @Override
    public void pay(Payment payment, Money payableMoney) {

    }
}
