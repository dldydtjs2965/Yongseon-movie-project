package com.dddsample.movieproject.presentation.discount;

import com.dddsample.movieproject.BaseIntegrationTest;
import com.dddsample.movieproject.domain.discount.application.SequenceDiscountService;
import com.dddsample.movieproject.domain.discount.model.DiscountErrorCode;
import com.dddsample.movieproject.domain.discount.model.enumberable.DiscountPolicyType;
import com.dddsample.movieproject.exception.CustomException;
import com.dddsample.movieproject.presentation.discount.request.DiscountPolicyRequestDto;
import com.dddsample.movieproject.presentation.discount.request.SequenceDiscountRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class DiscountIntegrationTest extends BaseIntegrationTest {
    private static LocalDate baseDate = LocalDate.of(2023, 5, 6);

    @MockBean
    private SequenceDiscountService sequenceDiscountService;
    @ParameterizedTest
    @MethodSource("할인_등록_파라미터")
    void 순서_할인_등록(SequenceDiscountRequestDto sequenceDiscountRequestDto) throws Exception {
        String payload = toJsonString(sequenceDiscountRequestDto);

        mockMvc.perform(post("/api/v1/discounts/sequence")
                .contentType("application/json")
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sequence").value(sequenceDiscountRequestDto.getDiscountSequence()))
                .andExpect(jsonPath("$.discountBaseDate").value(sequenceDiscountRequestDto.getDiscountBaseDate().toString()))
                .andExpect(jsonPath("$.discountPolicy.discountPolicyType").value(sequenceDiscountRequestDto.getDiscountPolicy().getDiscountPolicyType().toString()))
                .andExpect(jsonPath("$.discountPolicy.discountValue").value(sequenceDiscountRequestDto.getDiscountPolicy().getDiscountValue()));
    }

    private static Stream<Arguments> 할인_등록_파라미터() {
        return Stream.of(
                Arguments.of(
                        SequenceDiscountRequestDto.builder()
                                .discountSequence(1)
                                .discountBaseDate(baseDate)
                                .discountPolicy(DiscountPolicyRequestDto.builder()
                                        .discountValue(1000)
                                        .discountPolicyType(DiscountPolicyType.AMOUNT)
                                        .build())
                                .build()
                ),
                Arguments.of(
                        SequenceDiscountRequestDto.builder()
                                .discountSequence(2)
                                .discountBaseDate(baseDate)
                                .discountPolicy(DiscountPolicyRequestDto.builder()
                                        .discountValue(10)
                                        .discountPolicyType(DiscountPolicyType.PERCENT)
                                        .build())
                                .build()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("할인_값_유효성_검사_파라미터")
    void 할인_값_유효성_검사(SequenceDiscountRequestDto sequenceDiscountRequestDto) throws Exception {
        String payload = toJsonString(sequenceDiscountRequestDto);

        mockMvc.perform(post("/api/v1/discounts/sequence")
                .contentType("application/json")
                .content(payload))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> 할인_값_유효성_검사_파라미터() {
        return Stream.of(
                Arguments.of(
                        SequenceDiscountRequestDto.builder()
                                .discountSequence(1)
                                .discountBaseDate(baseDate)
                                .discountPolicy(DiscountPolicyRequestDto.builder()
                                        .discountValue(0)
                                        .discountPolicyType(DiscountPolicyType.AMOUNT)
                                        .build())
                                .build()
                ),
                Arguments.of(
                        SequenceDiscountRequestDto.builder()
                                .discountSequence(2)
                                .discountBaseDate(baseDate)
                                .discountPolicy(DiscountPolicyRequestDto.builder()
                                        .discountValue(101)
                                        .discountPolicyType(DiscountPolicyType.PERCENT)
                                        .build())
                                .build()
                )
        );
    }

    @Test
    void 중복할인_조건_입력_테스트() throws Exception{


        when(sequenceDiscountService.register(any()))
                .thenThrow(new CustomException(DiscountErrorCode.DUPLICATE_DISCOUNT));

        mockMvc.perform(post("/api/v1/discounts/sequence")
                .contentType("application/json")
                .content(중복_할인_요청()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(DiscountErrorCode.DUPLICATE_DISCOUNT.name()))
                .andExpect(jsonPath("$.message").value(DiscountErrorCode.DUPLICATE_DISCOUNT.getDetail()));

    }

    private String 중복_할인_요청() {
        return toJsonString(SequenceDiscountRequestDto.builder()
                .discountSequence(1)
                .discountBaseDate(baseDate)
                .discountPolicy(DiscountPolicyRequestDto.builder()
                        .discountValue(1000)
                        .discountPolicyType(DiscountPolicyType.AMOUNT)
                        .build())
                .build());
    }
}
