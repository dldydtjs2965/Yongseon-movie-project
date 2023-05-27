package com.dddsample.movieproject.domain.ticketing.model;

import com.dddsample.movieproject.common.component.Events;
import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.payment.model.vo.Payment;
import com.dddsample.movieproject.domain.ticketing.model.enumerable.TicketingStatus;
import com.dddsample.movieproject.exception.CustomException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;


@ExtendWith(MockitoExtension.class)/**/
class TicketingTest {
    @BeforeAll
    public static void setUp() {
        mockStatic(Events.class);
    }

    @Test
    @DisplayName("예매 성공")
    void successfulPay() {
        // given
        Ticketing ticketing = getTicketing();
        Money paidMoney = Money.of(1000);

        // when
        ticketing.pay(getPayment(), paidMoney);

        // then
        assertThat(ticketing.getTicketingStatus()).isEqualTo(TicketingStatus.PAID);
        assertThat(ticketing.getPaidMoney()).isEqualTo(paidMoney);
        assertThat(ticketing.getPaidAt()).isNotNull();
    }

    private Ticketing getTicketing() {
        return Ticketing.builder()
                .price(Money.of(1000))
                .screenId(1L)
                .ticketingStatus(TicketingStatus.RESERVED)
                .build();
    }

    @ParameterizedTest
    @MethodSource("getFailPayTicketing")
    @DisplayName("예매 실패 - 예약 상태가 아닌 경우")
    void failPayBecauseNotReserved(Ticketing ticketing) {
        // given
        Money paidMoney = Money.of(1000);

        // when, then
        assertThatThrownBy(() -> ticketing.pay(getPayment(), paidMoney))
                .isInstanceOf(CustomException.class)
                .isEqualTo(new CustomException(TicketingErrorCode.TICKETING_NOT_RESERVED));
    }

    private static Stream<Ticketing> getFailPayTicketing() {
        return Stream.of(
                Ticketing.builder()
                        .price(Money.of(1000))
                        .screenId(1L)
                        .ticketingStatus(TicketingStatus.PAID)
                        .build(),
                Ticketing.builder()
                        .price(Money.of(1000))
                        .screenId(1L)
                        .ticketingStatus(TicketingStatus.CANCELED)
                        .build()
        );
    }

    private Payment getPayment() {
        return Payment.of("1234-1234-1234-1234", LocalDate.of(2021, 10, 10), "123");
    }

    @Test
    @DisplayName("예매 취소")
    void cancel() {
        // given
        Ticketing ticketing = getPaidTicketing();

        // when
        ticketing.cancel();

        // then
        assertThat(ticketing.getTicketingStatus()).isEqualTo(TicketingStatus.CANCELED);
        assertThat(ticketing.getCanceledAt()).isNotNull();
    }

    private Ticketing getPaidTicketing() {
        return Ticketing.builder()
                .price(Money.of(1000))
                .screenId(1L)
                .ticketingStatus(TicketingStatus.PAID)
                .build();
    }

    @ParameterizedTest
    @DisplayName("예매 취소 실패 - 예매 상태가 아닌 경우")
    @MethodSource("getFailCancelTicketing")
    void failCancelBecauseNotPaid(Ticketing ticketing) {
        // given

        // when, then
        assertThatThrownBy(ticketing::cancel)
                .isInstanceOf(CustomException.class)
                .isEqualTo(new CustomException(TicketingErrorCode.PAID_TICKET_ONLY_CANCEL));
    }

    private static Stream<Ticketing> getFailCancelTicketing() {
        return Stream.of(
                Ticketing.builder()
                        .price(Money.of(1000))
                        .screenId(1L)
                        .ticketingStatus(TicketingStatus.RESERVED)
                        .build(),
                Ticketing.builder()
                        .price(Money.of(1000))
                        .screenId(1L)
                        .ticketingStatus(TicketingStatus.CANCELED)
                        .build()
        );
    }

}