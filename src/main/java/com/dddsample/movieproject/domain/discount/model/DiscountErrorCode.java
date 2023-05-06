package com.dddsample.movieproject.domain.discount.model;

import com.dddsample.movieproject.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DiscountErrorCode implements ErrorCode {
    PERCENT_AMOUNT_INVALID( "할인율은 0 ~ 100 사이의 값이어야 합니다.", HttpStatus.BAD_REQUEST);

    private final String detail;
    private final HttpStatus httpStatus;
}
