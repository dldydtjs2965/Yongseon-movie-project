package com.dddsample.movieproject.domain.ticketing.model;

import com.dddsample.movieproject.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TicketingErrorCode implements ErrorCode {
    NOT_FOUND_TICKETING(HttpStatus.NOT_FOUND, "Not found ticketing"),
    SOLD_OUT_TICKET(HttpStatus.BAD_REQUEST, "Sold out ticket"),
    ALREADY_STARTED_SCREEN(HttpStatus.BAD_REQUEST, "Started screening movie"),
    ALREADY_REFUNDED_TICKET(HttpStatus.BAD_REQUEST, "Refunded ticket"),
    TICKETING_NOT_RESERVED(HttpStatus.BAD_REQUEST, "Ticketing not reserved"),
    PAID_TICKET_ONLY_CANCEL(HttpStatus.BAD_REQUEST, "Paid ticket only cancel")
    ;



    private final HttpStatus httpStatus;

    private final String detail;
}
