package com.dddsample.movieproject.domain.ticketing.application;

import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.payment.model.vo.Payment;
import com.dddsample.movieproject.domain.screen.model.service.ReserveScreenService;
import com.dddsample.movieproject.domain.ticketing.infrastructure.TicketingRepository;
import com.dddsample.movieproject.domain.ticketing.model.Ticketing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketingService {
    private final TicketingRepository ticketingRepository;
    private final ReserveScreenService reserveScreenService;

    @Transactional
    public Ticketing ticketing(Ticketing ticketing, Payment payment) {
        Ticketing savedTicketing = ticketingRepository.save(ticketing);

        Money screenPrice = reserveScreenService.reserve(ticketing);

        return null;
    }
}
