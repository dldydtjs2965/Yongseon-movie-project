package com.dddsample.movieproject.presentation.ticketing.request;

import com.dddsample.movieproject.presentation.payment.request.PaymentRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TicketingRequestDto {
    @Valid
    private PaymentRequestDto payment;

    @Min(1)
    @NotNull
    private Integer ticketCount;
}
