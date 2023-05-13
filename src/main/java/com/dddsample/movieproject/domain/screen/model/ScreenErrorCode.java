package com.dddsample.movieproject.domain.screen.model;

import com.dddsample.movieproject.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ScreenErrorCode implements ErrorCode {
    INVALID_STARTED_AT(HttpStatus.BAD_REQUEST, "상영 시간이 올바르지 않습니다."),
    SCREENING_TIME_OVERLAP(HttpStatus.BAD_REQUEST, "상영 시간이 겹칩니다."),
    NOT_ENOUGH_TICKETS(HttpStatus.BAD_REQUEST, "예매할 수 있는 티켓이 부족합니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
