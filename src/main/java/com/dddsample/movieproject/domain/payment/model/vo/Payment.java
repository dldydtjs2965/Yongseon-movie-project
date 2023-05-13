package com.dddsample.movieproject.domain.payment.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class Payment {

    private String cardNumber;
    private LocalDate expirationDate;
    private String cvc;

    public Payment(String cardNumber, LocalDate expirationDate, String cvc) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvc = cvc;
    }

    public static Payment  of (String cardNumber, LocalDate expirationDate, String cvc) {
        return new Payment(cardNumber, expirationDate, cvc);
    }
}
