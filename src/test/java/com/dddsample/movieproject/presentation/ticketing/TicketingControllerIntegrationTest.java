package com.dddsample.movieproject.presentation.ticketing;

import com.dddsample.movieproject.BaseIntegrationTest;
import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.discount.model.DiscountErrorCode;
import com.dddsample.movieproject.domain.ticketing.application.TicketingService;
import com.dddsample.movieproject.domain.ticketing.model.Ticketing;
import com.dddsample.movieproject.domain.ticketing.model.TicketingErrorCode;
import com.dddsample.movieproject.domain.ticketing.model.enumerable.TicketingStatus;
import com.dddsample.movieproject.exception.CustomException;
import com.dddsample.movieproject.presentation.payment.request.PaymentRequestDto;
import com.dddsample.movieproject.presentation.ticketing.request.TicketingRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TicketingControllerIntegrationTest extends BaseIntegrationTest {
    @MockBean
    private TicketingService ticketingService;

    @Test
    void 티켓팅() throws Exception {
        when(ticketingService.ticketing(any(), any())).thenReturn(
                Ticketing.builder()
                        .price(Money.of(1000))
                        .paidAt(LocalDateTime.of(2021, 5, 6, 0, 0))
                        .ticketingStatus(TicketingStatus.PAID)
                        .build()
        );

        mockMvc.perform(post("/api/v1/screening/1/ticketing")
                .contentType("application/json")
                .content(getTicketingRequest()))
                .andExpect(status().isOk());
    }

    private String getTicketingRequest() {
        return toJsonString(
                new TicketingRequestDto(
                        new PaymentRequestDto(
                                "1234123412341234",
                                LocalDate.of(2023, 5, 6),
                                "123"
                        )
                )
        );
    }

    @Test
    void 티켓팅_할인_금액_오류() throws Exception {
        when(ticketingService.ticketing(any(), any())).thenThrow(new CustomException(DiscountErrorCode.OVER_DISCOUNT_AMOUNT));

        mockMvc.perform(post("/api/v1/screening/1/ticketing")
                .contentType("application/json")
                .content(getTicketingRequest()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(DiscountErrorCode.OVER_DISCOUNT_AMOUNT.name()))
                .andExpect(jsonPath("$.message").value(DiscountErrorCode.OVER_DISCOUNT_AMOUNT.getDetail()));
    }

    @Test
    void 티켓팅_품절() throws Exception {
        when(ticketingService.ticketing(any(), any())).thenThrow(new CustomException(TicketingErrorCode.SOLD_OUT_TICKET));

        mockMvc.perform(post("/api/v1/screening/1/ticketing")
                .contentType("application/json")
                .content(getTicketingRequest()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(TicketingErrorCode.SOLD_OUT_TICKET.name()))
                .andExpect(jsonPath("$.message").value(TicketingErrorCode.SOLD_OUT_TICKET.getDetail()));
    }

    @Test
    void 상영_시작후_티켓팅 () throws Exception {
        when(ticketingService.ticketing(any(), any())).thenThrow(new CustomException(TicketingErrorCode.ALREADY_STARTED_SCREEN));

        mockMvc.perform(post("/api/v1/screening/1/ticketing")
                .contentType("application/json")
                .content(getTicketingRequest()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(TicketingErrorCode.ALREADY_STARTED_SCREEN.name()))
                .andExpect(jsonPath("$.message").value(TicketingErrorCode.ALREADY_STARTED_SCREEN.getDetail()));
    }

    @ParameterizedTest
    @MethodSource("getPaymentArguments")
    void 티켓팅_값_유효성_검사(String cardNumber, LocalDate cardExpirationDate, String cardCvc) throws Exception {
        mockMvc.perform(post("/api/v1/screening/1/ticketing")
                .contentType("application/json")
                .content(toJsonString(
                        new TicketingRequestDto(
                                new PaymentRequestDto(
                                        cardNumber,
                                        cardExpirationDate,
                                        cardCvc
                                )
                        )
                )))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> getPaymentArguments() {
        return Stream.of(
                Arguments.of(" ", LocalDate.of(2023, 5, 6), "123"),
                Arguments.of("1234123412341234", null, "12"),
                Arguments.of("1234", LocalDate.of(2023, 5, 6), "1234"),
                Arguments.of("12341234123412341234", LocalDate.of(2020, 5, 6), "123")
        );
    }

    @Test
    void 티켓팅_환불() throws Exception{
        doNothing().when(ticketingService).refund(any());
        mockMvc.perform(post("/api/v1/screening/1/ticketing/1/refund"))
                .andExpect(status().isOk());
    }

    @Test
    void 시작된_영화_환불_불가() throws Exception{
        doThrow(new CustomException(TicketingErrorCode.ALREADY_STARTED_SCREEN)).when(ticketingService).refund(any());
        mockMvc.perform(post("/api/v1/screening/1/ticketing/1/refund"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(TicketingErrorCode.ALREADY_STARTED_SCREEN.name()))
                .andExpect(jsonPath("$.message").value(TicketingErrorCode.ALREADY_STARTED_SCREEN.getDetail()));
    }

    @Test
    void 이미_환불된_티켓_환불_불가() throws Exception{
        doThrow(new CustomException(TicketingErrorCode.ALREADY_REFUNDED_TICKET)).when(ticketingService).refund(any());
        mockMvc.perform(post("/api/v1/screening/1/ticketing/1/refund"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(TicketingErrorCode.ALREADY_REFUNDED_TICKET.name()))
                .andExpect(jsonPath("$.message").value(TicketingErrorCode.ALREADY_REFUNDED_TICKET.getDetail()));
    }
}