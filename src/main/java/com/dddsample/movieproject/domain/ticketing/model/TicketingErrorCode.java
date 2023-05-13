package com.dddsample.movieproject.domain.ticketing.model;

import com.dddsample.movieproject.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TicketingErrorCode implements ErrorCode {
    SOLD_OUT_TICKET(HttpStatus.BAD_REQUEST, "Sold out ticket"),
    ALREADY_STARTED_SCREEN(HttpStatus.BAD_REQUEST, "Started screening movie"),
    ALREADY_REFUNDED_TICKET(HttpStatus.BAD_REQUEST, "Refunded ticket")
    ;



    private final HttpStatus httpStatus;

    private final String detail;
}
