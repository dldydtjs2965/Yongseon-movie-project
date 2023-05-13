package com.dddsample.movieproject.domain.payment.model.vo;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Payment {

    private String cardNumber;
    private LocalDate expirationDate;
    private String cvc;
}
