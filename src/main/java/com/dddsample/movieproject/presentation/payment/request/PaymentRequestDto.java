package com.dddsample.movieproject.presentation.payment.request;

import com.dddsample.movieproject.domain.payment.model.vo.Payment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PaymentRequestDto {
    @NotEmpty
    @Size(min = 16, max = 16)
    private String cardNumber;
    @NotNull
    private LocalDate expirationDate;
    @NotEmpty
    @Size(min = 3, max = 3)
    private String cvc;

    public Payment toEntity() {
        return Payment.of(cardNumber, expirationDate, cvc);
    }
}
