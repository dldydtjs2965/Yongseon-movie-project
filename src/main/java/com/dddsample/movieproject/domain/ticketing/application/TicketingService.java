package com.dddsample.movieproject.domain.ticketing.application;

import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.payment.model.vo.Payment;
import com.dddsample.movieproject.domain.ticketing.model.TicketingErrorCode;
import com.dddsample.movieproject.domain.ticketing.model.service.TicketPaymentCalculator;
import com.dddsample.movieproject.domain.ticketing.infrastructure.TicketingRepository;
import com.dddsample.movieproject.domain.ticketing.model.Ticketing;
import com.dddsample.movieproject.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketingService {
    private final TicketingRepository ticketingRepository;
    private final TicketPaymentCalculator ticketPaymentCalculator;

    @Transactional
    public Ticketing ticketing(Ticketing ticketing, Payment payment) {
        Money payableMoney = ticketPaymentCalculator.calculatePayment(ticketing);

        ticketing.pay(payment, payableMoney);

        return ticketingRepository.save(ticketing);
    }

    @Transactional
    public void refund(Long ticketingId) {
        Ticketing ticketing = ticketingRepository.findById(ticketingId)
                .orElseThrow(() -> new CustomException(TicketingErrorCode.NOT_FOUND_TICKETING));

        ticketing.cancel();

        ticketingRepository.save(ticketing);
    }
}
