package com.dddsample.movieproject.presentation.ticketing.request;

import com.dddsample.movieproject.domain.ticketing.model.Ticketing;
import com.dddsample.movieproject.presentation.payment.request.PaymentRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketingRequestDto {
    @Valid
    private PaymentRequestDto payment;
}
